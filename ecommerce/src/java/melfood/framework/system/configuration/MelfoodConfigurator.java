/**
 * 2015 RpcConfigurator.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 * <p>
 * Licensed to the Utilities Software Services(USS).
 * For use this source code, you must obtain proper permission.
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system.configuration;

import java.io.File;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.NodeCombiner;
import org.apache.commons.configuration.tree.UnionCombiner;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import melfood.framework.Ctx;
import melfood.framework.uitl.FileUtils;

/**
 * This module play as a system configurater.<br>
 * This class will be start when system startup only once. To finish this configuration properly, this congigurater must know what kind of server type of current running.<br>
 * For this indication, we have to give JVM parameter option as EVN_TYPE<br>
 * ENV_TYPE can be take one of the following <br>
 * <ul>
 * <li>LOCAL</li>
 * <li>DEV</li>
 * <li>TEST</li>
 * <li>PROD</li>
 * </ul>
 * If EVN_TYPE parameter value is nothing, it will be run as a Local server. However if can't identified what kind of server with given parameter value, it will be make exception.
 *
 * @author Steven Min
 *
 */
public final class MelfoodConfigurator {
    // public static XMLConfiguration SYS_CONFIG = null;
    public static MelfoodConfig SYS_CONFIG = null;

    private static final Logger logger = LoggerFactory.getLogger(MelfoodConfigurator.class);

    private static MelfoodConfigurator singleton;

    private MelfoodConfigurator() throws Exception {

        Configuration melfood = new PropertiesConfiguration(System.getProperty("MELFOOD_CONFIG"));
        String env = melfood.getString("SYS.ENV_TYPE");

        String client = melfood.getString("SYS.CLIENT");
        String clientConfigurationFile = "";
        String commonConfigurationFile = "";

        try {
            if (StringUtils.isEmpty(client)) {
                // throw new Exception("Cannot understand what should choose for configuration file because there no information for which client");
                client = "melfood";
            }

            if (StringUtils.isBlank(env)) {
                env = "LOCAL";
            } else {
                if (!StringUtils.equalsIgnoreCase(env, "LOCAL") && !StringUtils.equalsIgnoreCase(env, "DEV") && !StringUtils.equalsIgnoreCase(env, "TEST")
                        && !StringUtils.equalsIgnoreCase(env, "TRAINING") && !StringUtils.equalsIgnoreCase(env, "PROD")) {
                    throw new Exception("Melfood eCommerce Engin can't decide what kind of server type. Please make sure server type by indicate ENV_TYPE as JVM option");
                } else {
                    env = StringUtils.upperCase(env);
                }
            }

            commonConfigurationFile = "/configuration/configuration.xml";
            clientConfigurationFile = "/configuration/configuration_" + client.toLowerCase() + ".xml";

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        // final File commonConfigURL = new File(getClass().getClassLoader().getResource(commonConfigurationFile).getFile());
        // final File clientConfigURL = new File(getClass().getClassLoader().getResource(clientConfigurationFile).getFile());
        final File commonConfigURL = FileUtils.convertInputStreamToFile(MelfoodConfigurator.class.getResourceAsStream(commonConfigurationFile));
        final File clientConfigURL = FileUtils.convertInputStreamToFile(MelfoodConfigurator.class.getResourceAsStream(clientConfigurationFile));

        try {
            MelfoodConfig commonConfig = new MelfoodConfig(commonConfigURL);
            MelfoodConfig clientConfig = new MelfoodConfig(clientConfigURL);

            NodeCombiner combiner = new UnionCombiner();
            CombinedConfiguration cc = new CombinedConfiguration(combiner);
            cc.addConfiguration(commonConfig);
            cc.addConfiguration(clientConfig);

            SYS_CONFIG = new MelfoodConfig(cc);
            SYS_CONFIG.setExpressionEngine(new XPathExpressionEngine());

            // Setting system configuration to UssContext static object
            Ctx.xmlConfig = SYS_CONFIG;
            Ctx.env = StringUtils.lowerCase(env);
            Ctx.client = client;
            Ctx.releaseVersion = SYS_CONFIG.getString("system-config/release-version");
            // Ctx.APP_DATA_DIR =  SYS_CONFIG.getVar("data-directory/dir");
            Ctx.APP_DATA_DIR = melfood.getString("FILE_ROOT_DIR");

            logger.info("Configuring by " + env + " mode");
            logger.info("  > Client code  : " + client);
            logger.info("  > Configurator : configuration.xml, " + clientConfigURL.getName());
            logger.info("  > Release version : " + Ctx.releaseVersion);
            logger.info("  > Rood data dir : " + Ctx.APP_DATA_DIR);

            System.setProperty("ENV_TYPE", StringUtils.upperCase(env));
            System.setProperty("CLIENT_CODE", StringUtils.upperCase(client));

            try {
                ApplicationProperties.appInit(melfood);
            } catch (Exception e) {
                throw new Exception("시스템 환경변수 값을 초기화 하는도중에 문제가 발생하였습니다. : " + e.getMessage());
            }

        } catch (Exception e) {
            logger.info(e.getMessage());
            // e.printStackTrace();
            throw e;
        }

    }

    public static MelfoodConfigurator getInstance() throws Exception {
        if (singleton == null) {
            singleton = new MelfoodConfigurator();
        }
        return singleton;
    }

    public XMLConfiguration getConfiguration() {
        return SYS_CONFIG;
    }

}
