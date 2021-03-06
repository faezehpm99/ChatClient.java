package massenger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import encrypt.MerkleHelman;
import utilities.Time;

import javax.swing.*;

public class ChatClient extends javax.swing.JFrame {

    public ChatClient() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMessage = new javax.swing.JTextArea();
        jButtonSend = new javax.swing.JButton();
        deleteMsg = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        massageList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jTextAreaMessage.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaMessage.setColumns(20);
        jTextAreaMessage.setRows(3);
        jTextAreaMessage.setTabSize(5);
        jScrollPane2.setViewportView(jTextAreaMessage);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButtonSend.setBackground(new java.awt.Color(204, 255, 255));
        jButtonSend.setIcon(new javax.swing.ImageIcon("/Users/faezehpirmohammadi/Desktop/Massenger/src/main/java/img/send.png")); // NOI18N
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSend, java.awt.BorderLayout.LINE_END);

        deleteMsg.setBackground(new java.awt.Color(255, 204, 204));
        deleteMsg.setIcon(new javax.swing.ImageIcon("/Users/faezehpirmohammadi/Desktop/Massenger/src/main/java/img/icons8-delete-100-2.png")); // NOI18N
        deleteMsg.setText("DeleteAll");
        deleteMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMsgActionPerformed(evt);
            }
        });
        jPanel1.add(deleteMsg, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
        jScrollPane3.setViewportView(massageList);

        getContentPane().add(jScrollPane3, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 531, 587);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       try {
            // TODO add your handling code here:
            socket = new Socket("localhost", 5678);
            scanner = new Scanner(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            data = new Vector();

          // massageList.setListData(data);
            Thread myThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String response = merkleHelman.decryptMsg(scanner.nextLine());
                        String time =response.substring(0,7);
                        String massage=response.substring(8);

                            data.addElement(massage+'\t'+time+'\n');
                            massageList.setListData(data);

                    }
                }
            });
            myThread.start();
           massageList.addMouseListener(new MouseAdapter() {
               public void mouseClicked(MouseEvent me) {
                   if (me.getClickCount() == 1) {
                       JList target = (JList)me.getSource();
                       int index = target.locationToIndex(me.getPoint());
                       if (index >= 0) {
                           int res=  JOptionPane.showOptionDialog(null, "Are you sure to Delete Permanatly?", "warn", JOptionPane.DEFAULT_OPTION,
                                   JOptionPane.INFORMATION_MESSAGE, null, null, null);
                           if (res==0){
                               data.remove(index);
                               massageList.setListData(data);
                           }
                       }
                   }
               }
           });
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }




    }//GEN-LAST:event_formWindowOpened

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        // TODO add your handling code here:
        time = new Time();
        String massage = jTextAreaMessage.getText();
        if(massage!=null){
            writer.println( merkleHelman.encryptMsg(time.getTime()+massage));
            jTextAreaMessage.setText(" ");
            writer.flush();
        }

    }//GEN-LAST:event_jButtonSendActionPerformed

    private void deleteMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMsgActionPerformed
        // TODO add your handling code here:
        data.removeAllElements();
        massageList.setListData(data);
    }//GEN-LAST:event_deleteMsgActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient().setVisible(true);
            }
        });
    }
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;
    Time time ;
    MerkleHelman merkleHelman = new MerkleHelman();
    private JScrollPane jsp;
    private Vector data;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteMsg;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaMessage;
    private javax.swing.JList<String> massageList;
    // End of variables declaration//GEN-END:variables
}
