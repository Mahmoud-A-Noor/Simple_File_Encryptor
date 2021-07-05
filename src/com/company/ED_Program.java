package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ED_Program extends JDialog {
    private JPanel contentPane;
    private JPanel panel1;
    private JSlider slider1;
    private JButton button;
    private JTextField textField1;
    private JButton button1;
    private JRadioButton decryptRadioButton;
    private JRadioButton encryptRadioButton;
    private JLabel sliderLabel;
    private JButton buttonOK;
    private JButton buttonCancel;

    public ED_Program() {
        this.setVisible(true);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.setSize(550,275);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        encryptRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                button.setText("Encrypt");
                encryptRadioButton.setSelected(true);
                decryptRadioButton.setSelected(false);
            }
        });
        decryptRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                button.setText("Decrypt");
                decryptRadioButton.setSelected(true);
                encryptRadioButton.setSelected(false);
            }
        });
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                JFileChooser chooser=new JFileChooser();

                //FileNameExtensionFilter filter=new FileNameExtensionFilter("TEXT_FILES","txt");
                //chooser.setFileFilter(filter);

                chooser.addChoosableFileFilter(new FileNameExtensionFilter("PDF","pdf"));
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("DOCX","docx"));
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("HTML","html"));

                int result=chooser.showOpenDialog(getParent());

                if(result==0)
                {
                    File path=chooser.getSelectedFile();
                    textField1.setText(path.toString());
                }
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                if(encryptRadioButton.isSelected())
                {
                    FileInputStream inputStream=null;
                    FileOutputStream outputStream=null;

                    BufferedInputStream bread=null;
                    BufferedOutputStream bwrite=null;

                    JFileChooser fileChooser=new JFileChooser();
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("TEXT","txt"));
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MP4","mp4"));

                    int result=fileChooser.showSaveDialog(getParent());
                    if(result==0)
                    {
                        try {
                            inputStream=new FileInputStream(textField1.getText());
                            outputStream=new FileOutputStream(fileChooser.getSelectedFile()+textField1.getText().substring(textField1.getText().lastIndexOf('.')));

                            bread=new BufferedInputStream(inputStream);
                            bwrite=new BufferedOutputStream(outputStream);

                            int c;

                            while((c=bread.read())!=-1)
                                bwrite.write(c+slider1.getValue());

                            JOptionPane.showMessageDialog(getParent(),"تم تشفير الملف");

                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }finally {
                            try {
                                inputStream.close();
                                outputStream.close();
                                new File(textField1.getText()).delete();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }else
                    {
                        JOptionPane.showMessageDialog(getParent(),"اختر مكان حفظ الملف");
                    }

                }
                else if(decryptRadioButton.isSelected())
                {
                    FileInputStream inputStream=null;
                    FileOutputStream outputStream=null;

                    BufferedInputStream bread=null;
                    BufferedOutputStream bwrite=null;

                    JFileChooser save=new JFileChooser();
                    save.addChoosableFileFilter(new FileNameExtensionFilter("TEXT","txt"));
                    save.addChoosableFileFilter(new FileNameExtensionFilter("MP4","mp4"));

                    int result=save.showSaveDialog(getParent());
                    if(result==0)
                    {
                        try {
                            inputStream=new FileInputStream(textField1.getText());
                            outputStream=new FileOutputStream(save.getSelectedFile()+textField1.getText().substring(textField1.getText().lastIndexOf('.')));

                            bread=new BufferedInputStream(inputStream);
                            bwrite=new BufferedOutputStream(outputStream);

                            int c;

                            while((c=bread.read())!=-1)
                                bwrite.write(c-slider1.getValue());

                            JOptionPane.showMessageDialog(getParent(),"تم فك تشفير الملف");

                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }finally {
                            try {
                                inputStream.close();
                                outputStream.close();
                                new File(textField1.getText()).delete();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }else
                    {
                        JOptionPane.showMessageDialog(getParent(),"اختر مكان حفظ الملف");
                    }
                }
            }
        });
        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged( ChangeEvent e ) {
                sliderLabel.setText(String.valueOf(slider1.getValue()));
            }
        });
    }

    public static void main( String[] args ) {
        ED_Program dialog = new ED_Program();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
