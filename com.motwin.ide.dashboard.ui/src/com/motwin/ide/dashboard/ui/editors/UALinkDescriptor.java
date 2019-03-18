/**
 * 
 */
package com.motwin.ide.dashboard.ui.editors;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

import com.google.common.collect.ImmutableMap;
import com.motwin.ide.core.commons.StatusHandler;
import com.motwin.ide.dashboard.internal.ui.DashboardPlugin;
import com.motwin.ide.dashboard.ui.DashboardImages;

public final class UALinkDescriptor extends AbstractDescriptor {
    public enum Type {
        PROJECT("project"), SAMPLE("sample"), CHEATSHEET("cheatsheet"), EXPORT("export"), DOC("doc");

        private static final Map<String, Type> types = ImmutableMap.of(PROJECT.getLabel(), PROJECT, SAMPLE.getLabel(),
                                                             SAMPLE, CHEATSHEET.getLabel(), CHEATSHEET,
                                                             EXPORT.getLabel(), EXPORT, DOC.getLabel(), DOC);
        private String                         label;

        Type(final String aLabel) {
            label = aLabel;
        }

        public static Type getType(final String aLabel) {
            return types.get(aLabel);
        }

        public String getLabel() {
            return label;
        }

    }

    private final FormToolkit toolkit;

    static final String       ELEMENT_IMAGE      = "image";

    static final String       ATTRIBUTE_TYPE     = "type";

    static final String       ATTRIBUTE_TEXT     = "text";

    static final String       ATTRIBUTE_PART     = "part";

    static final String       ATTRIBUTE_LISTENER = "listener";

    public UALinkDescriptor(final IConfigurationElement anElement, final FormToolkit aToolkit) {
        super(anElement);
        toolkit = aToolkit;
    }

    public String getPart() {
        String part;
        part = getElement().getAttribute(UALinkDescriptor.ATTRIBUTE_PART);

        return part;
    }

    public String getText() {
        String text;
        text = getElement().getAttribute(UALinkDescriptor.ATTRIBUTE_TEXT);

        return text;
    }

    public Type getType() {
        String typeAttr;
        typeAttr = getElement().getAttribute(UALinkDescriptor.ATTRIBUTE_TYPE);

        Type type;
        type = Type.getType(typeAttr);

        return type;
    }

    public PluginImageDescriptor getImage() {
        PluginImageDescriptor image;
        image = null;

        IConfigurationElement[] elements;
        elements = getElement().getChildren(UALinkDescriptor.ELEMENT_IMAGE);

        if (elements.length == 1) {
            image = new PluginImageDescriptor(elements[0]);

        } else if (elements.length > 1) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "found more than one ["
                + UALinkDescriptor.ELEMENT_IMAGE + "] for [" + getId() + "]"));

        }

        return image;
    }

    public ImageHyperlink createHyperlink(final Composite aParent) {
        ImageHyperlink link;
        link = null;

        try {

            IConfigurationElement element;
            element = getElement();

            Object object;
            object = element.createExecutableExtension(UALinkDescriptor.ATTRIBUTE_LISTENER);

            if (object instanceof IHyperlinkListener) {
                IHyperlinkListener listener;
                listener = (IHyperlinkListener) object;

                link = toolkit.createImageHyperlink(aParent, SWT.UNDERLINE_LINK);
                link.setText(getText());
                link.addHyperlinkListener(listener);

                PluginImageDescriptor pluginImage;
                pluginImage = getImage();

                Image image;
                image = null;

                if (pluginImage == null) {
                    image = getDefaultImage();

                } else {
                    image = DashboardImages.INSTANCE.getImage(pluginImage.getPluginID(), pluginImage.getPath());

                }

                link.setImage(image);

            } else {
                StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Cannot load "
                    + object.getClass().getCanonicalName() + " must implement "
                    + IHyperlinkListener.class.getCanonicalName()));
            }

        } catch (CoreException e) {
            StatusHandler.log(new Status(IStatus.ERROR, DashboardPlugin.PLUGIN_ID, "Cannot read UALinkDescriptor [id="
                + getId() + ", text=" + getText() + "]", e));
        }

        return link;
    }

    private Image getDefaultImage() {
        Image image;
        image = null;

        Type type;
        type = getType();

        switch (type) {
            case PROJECT:
                image = DashboardImages.INSTANCE.getImage("icons/etool16/motwin_newprj_wiz.gif");
                break;

            case SAMPLE:
                image = DashboardImages.INSTANCE.getImage("icons/etool16/motwin_newsampleprj_wiz.gif");
                break;

            case CHEATSHEET:
                image = DashboardImages.INSTANCE.getImage("icons/etool16/start_cheatsheet.gif");
                break;

            case EXPORT:
                image = DashboardImages.INSTANCE.getImage("icons/etool16/export_wiz.gif");
                break;

            default:
                break;
        }

        return image;
    }

}