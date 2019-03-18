/**
 * 
 */
package com.motwin.ide.html5.ui.exports.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.google.common.base.Preconditions;

/**
 * @author fbou
 * 
 */
public class ReadmeDialog extends Dialog {

    private final String title;
    private final String description;
    private final String url;
    protected Object     result;
    protected Shell      shell;

    public ReadmeDialog(Shell aParent, int aStyle, String aTitle, String aDescription, String aUrl) {
        super(aParent, aStyle);
        title = Preconditions.checkNotNull(aTitle);
        description = Preconditions.checkNotNull(aDescription);
        url = Preconditions.checkNotNull(aUrl);
    }

    public ReadmeDialog(Shell aParent, String aTitle, String aDescription, String aUrl) {
        this(aParent, SWT.NONE, aTitle, aDescription, aUrl);
    }

    public Object open() {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return result;
    }

    protected void createContents() {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setSize(640, 480);
        shell.setText(title);
        shell.setLayout(new GridLayout(1, false));

        Label projectVersionLabel;
        projectVersionLabel = new Label(shell, SWT.NULL);
        projectVersionLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        projectVersionLabel.setText(description);

        Browser browser;
        browser = new Browser(shell, SWT.BORDER);
        browser.setLayoutData(new GridData(GridData.FILL_BOTH));
        browser.setUrl(url);

        Button okButton;
        okButton = new Button(shell, SWT.NULL);
        okButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        okButton.setText("Close");
        okButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent aArg0) {
                shell.close();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent aArg0) {
                shell.close();
            }
        });
    }
}
