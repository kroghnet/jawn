package net.javapla.jawn.core.configuration;

import net.javapla.jawn.core.util.Constants;

public class DeploymentInfo {

	private final String WEBAPP;
//	private final String CONTEXT_PATH;
//	private final boolean CONTEXT_PATH_SET;
	
	public DeploymentInfo(JawnConfigurations properties) {
		WEBAPP = assertPath(properties.getSecure(Constants.PROPERTY_DEPLOYMENT_INFO_WEBAPP_PATH).orElse(""));
//		CONTEXT_PATH = properties.getSecure(Constants.PROPERTY_DEPLOYMENT_INFO_CONTEXT_PATH).orElse("");
//		CONTEXT_PATH_SET = !CONTEXT_PATH.isEmpty();
	}
	
	private static String assertPath(String path) {
		if (!path.endsWith("/"))
			return path + "/";
		return path;
	}
	
	/**
     * Returns a String containing the real path for a given virtual path. For example, the path "/index.html" returns
     * the absolute file path on the server's filesystem would be served by a request for
     * "http://host/contextPath/index.html", where contextPath is the context path of this ServletContext.
     * 
     * <p>The real path returned will be in a form appropriate to the computer and operating system on which the servlet
     * container is running, including the proper path separators. This method returns null if the servlet container
     * cannot translate the virtual path to a real path for any reason (such as when the content is being made
     * available from a .war archive).</p>
     *
     * <p>
     * JavaDoc copied from: <a href="http://download.oracle.com/javaee/1.3/api/javax/servlet/ServletContext.html#getRealPath%28java.lang.String%29">
     * http://download.oracle.com/javaee/1.3/api/javax/servlet/ServletContext.html#getRealPath%28java.lang.String%29</a>
     * </p>
     *
     * @param path a String specifying a virtual path
     * @return a String specifying the real path, or null if the translation cannot be performed
     */
    public String getRealPath(String path) {
    	if (path == null) return null;
//    	if (CONTEXT_PATH_SET && path.startsWith(CONTEXT_PATH)) return WEBAPP + path.substring(CONTEXT_PATH.length());
        return WEBAPP + path;
    }
    
    /*public String getContextPath() {
        return CONTEXT_PATH;
    }
    
    public String translateIntoContextPath(String path) {
        if (!CONTEXT_PATH_SET) 
            return path;
        if (path.charAt(0) == '/')
            return CONTEXT_PATH + path;
        return CONTEXT_PATH + '/' + path;
    }
    
    public void translateIntoContextPath(String[] paths) {
        if (CONTEXT_PATH_SET) {
            for (int i = 0; i < paths.length; i++) {
                paths[i] = translateIntoContextPath(paths[i]);
            }
        }
    }
    
    public String stripContextPath(String path) {
        if (!CONTEXT_PATH_SET) return path;
        return path.substring(CONTEXT_PATH.length());
    }*/
}
