/**
 * 
 */
package com.motwin.ide.server.ui.commands;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.wst.server.core.IServer;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.google.common.base.Preconditions;
import com.motwin.ide.ui.operations.OpenBrowserOperation;

/**
 * @author ctranxuan
 * 
 */
public class OpenWebConsoleCommand extends AbstractHandler {

    private static final String SERVICE_ELEMENT        = "Service";

    private static final String PORT_ATTR              = "port";

    private static final String SSL_ENABLED_ATTR       = "SSLEnabled";

    private static final String PROTOCOL_ATTR          = "protocol";

    private static final String CONNECTOR_ELEMENT      = "Connector";

    private static final String NAME_ATTR              = "name";

    private static final String HOST_ELEMENT           = "Host";

    private static final String ENGINE_ELEMENT         = "Engine";

    private static final String TOMCAT_SERVER_XML_PATH = IPath.SEPARATOR + "configuration" + IPath.SEPARATOR
                                                           + "tomcat-server.xml";

    public static final String  COMMAND_ID             = "com.motwin.ide.server.ui.commands.runtime.openWebConsoleCommand";

    public OpenWebConsoleCommand() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object execute(final ExecutionEvent anEvent) throws ExecutionException {
        ISelection currentSelection;
        currentSelection = HandlerUtil.getCurrentSelection(anEvent);

        try {
            if (currentSelection instanceof IStructuredSelection) {

                IStructuredSelection selection;
                selection = (IStructuredSelection) currentSelection;

                Iterator<Object> iterator;
                iterator = selection.iterator();

                if (iterator.hasNext()) {
                    // there should be only one selection at this stage
                    // (guaranteed
                    // by the parent container menu)
                    IServer server;
                    server = (IServer) iterator.next();

                    IPath runtimeLocation;
                    runtimeLocation = server.getRuntime().getLocation();

                    if (server.getServerState() == IServer.STATE_STARTED) {
                        IPath serverXml;
                        serverXml = runtimeLocation.append(TOMCAT_SERVER_XML_PATH);
                        File serverFile;
                        serverFile = serverXml.toFile();

                        Preconditions.checkState(serverFile.exists(),
                                "Tomcat configuration file [" + serverFile.getName() + "] does not exist");

                        String url;
                        url = buildURL(serverFile);

                        new OpenBrowserOperation(url).run();

                    } else {
                        IWorkbench workbench;
                        workbench = PlatformUI.getWorkbench();

                        IWorkbenchWindow window;
                        window = workbench.getActiveWorkbenchWindow();

                        MessageDialog.openError(window.getShell(), "The MOS Server is not running",
                                "Cannot open the MOS Web Console: the MOS server is not running");
                    }
                }
            }

        } catch (JDOMException e) {
            throw new ExecutionException("failed to parse the " + "tomcat-server.xml" + " of the selected runtime ["
                + currentSelection + "] for command [ID=" + COMMAND_ID + "]", e);
        } catch (IOException e) {
            throw new ExecutionException("an I/O error occurred while trying to access the " + "tomcat-server.xml"
                + " of the selected runtime [" + currentSelection + "] for command [ID=" + COMMAND_ID + "]", e);
        }

        return null;
    }

    private String buildURL(final File aFile) throws JDOMException, IOException {
        SAXBuilder builder;
        builder = new SAXBuilder();

        Document document;
        document = builder.build(aFile);

        Element root;
        root = document.getRootElement();

        Element service;
        service = root.getChild(SERVICE_ELEMENT);

        String port;
        port = extractPort(service);

        Preconditions.checkState(port != null, "failed to parse the Tomcat port for the file [" + aFile + "].");

        String host;
        host = extractHost(service);

        String url;
        url = "http://" + host + ":" + port + "/mos";

        return url;
    }

    private String extractHost(final Element aServiceElement) {
        Element engine;
        engine = aServiceElement.getChild(ENGINE_ELEMENT);

        Element host;
        host = engine.getChild(HOST_ELEMENT);

        String hostName;
        hostName = host.getAttributeValue(NAME_ATTR);
        return hostName;
    }

    @SuppressWarnings("unchecked")
    private String extractPort(final Element aServiceElement) {
        List<Element> connectors;
        connectors = aServiceElement.getChildren(CONNECTOR_ELEMENT);

        Iterator<Element> iterator;
        iterator = connectors.iterator();

        String port;
        port = null;

        while (iterator.hasNext() && port == null) {
            Element connector;
            connector = iterator.next();

            if ("HTTP/1.1".equals(connector.getAttributeValue(PROTOCOL_ATTR))
                && connector.getAttribute(SSL_ENABLED_ATTR) == null) {
                port = connector.getAttributeValue(PORT_ATTR);
            }
        }
        return port;
    }
}
