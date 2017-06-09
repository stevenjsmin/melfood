/**
 * Copyright (c) 1015, 2016, Utilities Software Services(USS)and/or its affiliates. All rights reserved.
 * USS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * Created : FTPClient.java, 2:00:10 pm, by Steven
 *
 */

package melfood.framework.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import melfood.framework.Ctx;

/**
 * This is FTP Utilities with secured protocol
 * 
 * @author Steven
 *
 */
public class FTPClient {

	private static final Logger logger = LoggerFactory.getLogger(FTPClient.class);

	private static String ftpHost = "";
	private static int ftpPort = 22;
	private static String ftpUser = "";
	private static String ftpPassword = "";
	private static String ftpKeyPath = "";

	public FTPClient() {
		this.ftpHost = Ctx.xmlConfig.getVar("orion/ftp-connection/host");
		this.ftpPort = Integer.parseInt(Ctx.xmlConfig.getVar("orion/ftp-connection/port"));
		this.ftpUser = Ctx.xmlConfig.getVar("orion/ftp-connection/user");
		this.ftpPassword = Ctx.xmlConfig.getVar("orion/ftp-connection/password");
		this.ftpKeyPath = Ctx.xmlConfig.getVar("system-config/server-key-file/file");

	}

	public FTPClient(String ftpHost, int ftpPort, String ftpUser, String ftpPassword) {
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;
	}

	public FTPClient(String ftpHost, int ftpPort, String ftpUser, String ftpPassword, String ftpKeyPath) {
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		this.ftpUser = ftpUser;
		this.ftpPassword = ftpPassword;
		this.ftpKeyPath = ftpKeyPath;
	}

	/**
	 * Get SFTP Session object <br>
	 * As default, it trying to get session with private key and without password
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Session getSession() throws Exception {
		return getSession(false, true);
	}

	/**
	 * Get SFTP Session object
	 * 
	 * @param withPassword
	 * @param withPrivateKey
	 * @return
	 * @throws Exception
	 */
	public static Session getSession(boolean withPassword, boolean withPrivateKey) throws Exception {

		if (StringUtils.isBlank(ftpHost)) ftpHost = Ctx.xmlConfig.getVar("orion/ftp-connection/host");
		if (StringUtils.isBlank(ftpUser)) ftpUser = Ctx.xmlConfig.getVar("orion/ftp-connection/user");
		if (StringUtils.isBlank(ftpPassword)) ftpPassword = Ctx.xmlConfig.getVar("orion/ftp-connection/password");
		if (StringUtils.isBlank(ftpKeyPath)) ftpKeyPath = Ctx.xmlConfig.getVar("system-config/server-key-file/file");

		// logger.info("ftpHost : {}", ftpHost);
		// logger.info("ftpUser : {}", ftpUser);
		// logger.info("ftpPassword : {}", "****");
		// logger.info("ftpKeyPath : {}", ftpKeyPath);

		try {
			Session session = null;
			JSch jsch = new JSch();
			if (withPrivateKey) jsch.addIdentity(ftpKeyPath);

			session = jsch.getSession(ftpUser, ftpHost, ftpPort);

			if (withPassword) session.setPassword(ftpPassword);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect();

			return session;
		} catch (JSchException e) {
			throw e;
		}
	}

	/**
	 * File put using connection information of default and source file and destination<br>
	 * (This method was decprecated. Please use FTPClient.sshPut(String, String)
	 * 
	 * @param localFileFullPath
	 * @param remoteDir
	 * @deprecated
	 */
	public static void sshPutToOrionSharedDir(String localFileFullPath, String remoteDir) {
		FTPClient.sshPut(localFileFullPath, remoteDir);
	}

	/**
	 * File put using connection information of default and source file and destination <br>
	 * (This method was decprecated. Please use FTPClient.sshGet(String, String)
	 * 
	 * @param remoteDir
	 * @param remoteFile
	 * @param toLocalDir
	 * @deprecated
	 */
	public static void sshGetFromOrionSharedDir(String remoteDir, String remoteFile, String toLocalDir) {

		FTPClient.sshGet(remoteDir, remoteFile, toLocalDir);
	}

	/**
	 * File put using following parameters
	 * 
	 * @param localFileFullPath Source file that will be transfered
	 * @param remoteDir Destination to be copied
	 */
	public static void sshPut(String localFileFullPath, String remoteDir) {

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			session = getSession();
			channel = session.openChannel("sftp");
			channel.connect();

			FileInputStream in = null;
			try {
				in = new FileInputStream(localFileFullPath);
				channelSftp = (ChannelSftp) channel;
				channelSftp.cd(remoteDir);
				channelSftp.put(in, new File(localFileFullPath).getName());

				logger.info("File upload via SFTP : {}", localFileFullPath);

			} catch (SftpException e) {
				// e.printStackTrace();
			} catch (FileNotFoundException e) {
				// e.printStackTrace();
				logger.warn("Failure while process via SFTP [1][" + localFileFullPath + "] : " + e.getMessage());
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// e.printStackTrace();
					logger.warn("Failure while process via SFTP [2][" + localFileFullPath + "] : " + e.getMessage());
				}

				try {
					session.disconnect();
				} catch (Exception e) {
					// e.printStackTrace();
					logger.warn("Failure while process via SFTP [3][" + localFileFullPath + "] : " + e.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("Failure while process via SFTP [4][" + localFileFullPath + "] : " + e.getMessage());
		}
	}

	/**
	 * Get file(s) from remote server<br>
	 * 
	 * @param remoteDir
	 * @param remoteFile It could be a file or file pattern with wildecard character
	 * @param toLocalDir
	 */
	public static void sshGet(String remoteDir, String remoteFile, String toLocalDir) {

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			session = getSession();
			channel = session.openChannel("sftp");
			channel.connect();

			try {
				channelSftp = (ChannelSftp) channel;
				channelSftp.cd(remoteDir);
				channelSftp.get(remoteFile, toLocalDir);

				logger.info("File download via SFTP : {}", remoteFile);

			} catch (SftpException e) {
				// e.printStackTrace();
				logger.warn("Failure while process via SFTP [1] [" + remoteFile + "] : " + e.getMessage());

			} catch (Exception e) {
				// e.printStackTrace();
				logger.warn("Failure while process via SFTP [2] [" + remoteFile + "] : " + e.getMessage());
			} finally {
				try {
					channelSftp.disconnect();
				} catch (Exception e) {
					// e.printStackTrace();
					logger.warn("Failure while process via SFTP [3] [" + remoteFile + "] : " + e.getMessage());
				}

				try {
					session.disconnect();
				} catch (Exception e) {
					// e.printStackTrace();
					logger.warn("Failure while process via SFTP [4] [" + remoteFile + "] : " + e.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// logger.info("Failure while process via SFTP [5] at " + Thread.currentThread().getStackTrace()[2].getLineNumber() + "[" + file + "] : " + e.getMessage());
			logger.warn("Failure while process via SFTP [5] [" + remoteFile + "] : " + e.getMessage());
		}
	}

}
