/** 
 * 2015 UssTilesView.java
 * Created by Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *  
 * Licensed to the Utilities Software Services(USS). 
 * For use this source code, you must obtain proper permission. 
 * Or enforcement is prohibited by applicable law, you may not modify, decompile, or reverse engineer Software.
 */

package melfood.framework.system;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.servlet.context.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.JstlUtils;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.tiles2.TilesView;
import org.springframework.web.servlet.view.tiles2.TilesViewResolver;

/**
 * @author Steven J.S Min(steven.min@utilitiessoftwareservices.com)
 *
 */
public class MelfoodTilesView extends TilesView {

	private static final Logger logger = LoggerFactory.getLogger(MelfoodTilesView.class);

	private boolean alwaysInclude = false;

	/**
	 * Specify whether to always include the view rather than forward to it.
	 * <p>
	 * Default is "false". Switch this flag on to enforce the use of a Servlet include, even if a forward would be possible.
	 * 
	 * @since 4.1.2
	 * @see TilesViewResolver#setAlwaysInclude
	 */
	@Override
	public void setAlwaysInclude(boolean alwaysInclude) {
		super.setAlwaysInclude(alwaysInclude);
		this.alwaysInclude = alwaysInclude;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext servletContext = getServletContext();
		TilesContainer container = ServletUtil.getContainer(servletContext);
		if (container == null) {
			throw new ServletException("Tiles container is not initialized. " + "Have you added a TilesConfigurer to your web application context?");
		}

		exposeModelAsRequestAttributes(model, request);
		JstlUtils.exposeLocalizationContext(new RequestContext(request, servletContext));
		if (this.alwaysInclude) {
			ServletUtil.setForceInclude(request, true);
		}

		String tilesUrl = getUrl();
		String userAgent = request.getHeader("User-Agent");
		String httpAccept = request.getHeader("Accept");
		UAgentInfo detector = new UAgentInfo(userAgent, httpAccept);

		// Insert moile key, when the request is come from mobile devices
		// if (detector.detectMobileQuick()) {
		// tilesUrl = "mobile/" + getUrl();
		// }
		// logger.info("Request client information  : " + detector.getUserAgent());

		container.render(tilesUrl, request, response);
	}

}
