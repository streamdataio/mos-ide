package com.motwin.ide.server.ui.editor.virtualdb.contentassist;

import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.springframework.ide.eclipse.beans.ui.editor.hyperlink.BeanHyperlinkCalculator;
import org.springframework.ide.eclipse.beans.ui.editor.hyperlink.bean.BeansHyperlinkDetector;

/**

 */
public class VirtualdbHyperlinkDetector extends BeansHyperlinkDetector implements IHyperlinkDetector {

    @Override
    public void init() {
        BeanHyperlinkCalculator beanRef = new BeanHyperlinkCalculator();
        registerHyperlinkCalculator("ref", beanRef);
    }

}
