package massenger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import encrypt.MerkleHelman;
import java.awt.List;
import utilities.Time;

import javax.swing.*;

public class ChatServer extends javax.swing.JFrame {

    public ChatServer() {
        initComponents();


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaMessage = new javax.swing.JTextArea();
        jButtonSend = new javax.swing.JButton();
        jDeleteBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        MassageList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jTextAreaMessage.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaMessage.setColumns(20);
        jTextAreaMessage.setRows(5);
        jTextAreaMessage.setSelectionEnd(100);
        jTextAreaMessage.setSelectionStart(100);
        jScrollPane2.setViewportView(jTextAreaMessage);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButtonSend.setBackground(new java.awt.Color(204, 255, 255));
        jButtonSend.setText("SEND");
        //jButtonSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/massenger/send.png"))); // NOI18N
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSend, java.awt.BorderLayout.LINE_END);

        jDeleteBtn.setBackground(new java.awt.Color(255, 204, 204));
       // jDeleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-delete-100-2.png"))); // NOI18N
        jDeleteBtn.setText("DeleteAll");
        jDeleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteBtnActionPerformed(evt);
            }
        });
        jPanel1.add(jDeleteBtn, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
       /* if(data!=null){
            MassageList.setListData(data);

        }*/
       /* MassageList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });*/
        jScrollPane3.setViewportView(MassageList);

        getContentPane().add(jScrollPane3, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 545, 581);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            // TODO add your handling code here:
            serverSocket = new ServerSocket(5678);
            socket = serverSocket.accept();
            scanner = new Scanner(socket.getInputStream());
            writer=new PrintWriter(socket.getOutputStream(),true);
            data = new Vector();
           Thread myThread = new Thread(new Runnable() {
               @Override
               public void run() {
                   while(true){
                       String response = scanner.nextLine();
                       String decMassage=merkleHelman.decryptMsg(response);
                       String time=decMassage.substring(0,7);
                       String massage=decMassage.substring(8);
                       data.addElement( massage+'\t'+time+'\n');
                       MassageList.setListData(data);

                   }
               }
           });
         myThread.start();
            MassageList.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    if (me.getClickCount() == 1) {
                        JList target = (JList)me.getSource();
                        int index = target.locationToIndex(me.getPoint());
                        if (index >= 0) {
                            Object item = target.getModel().getElementAt(index);
                           int res=  JOptionPane.showOptionDialog(null, "Are you sure to Delete Permanatly?", "warn", JOptionPane.DEFAULT_OPTION,
                           JOptionPane.INFORMATION_MESSAGE, null, null, null);
                           if (res==0){
                               data.remove(index);
                               MassageList.setListData(data);
                           }
                        }
                    }
                }
            });
             
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed

             ClientHandler handler = new ClientHandler(serverSocket,socket, scanner,writer,time,merkleHelman,jTextAreaMessage);
             clientHandlers.add(handler);
        for (int i = 0; i < clientHandlers.size(); i++) {
            Thread thread  = new Thread(clientHandlers.get(i));
            thread.start();

        }



         }


//GEN-LAST:event_jButtonSendActionPerformed

    private void jDeleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteBtnActionPerformed
        // TODO add your handling code here:
        data.removeAllElements();
        MassageList.setListData(data);

    }//GEN-LAST:event_jDeleteBtnActionPerformed

                                           
    
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
                new ChatServer().setVisible(true);
            }
        });
    }




    private ServerSocket serverSocket;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter writer;
    private Time time;
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newScheduledThreadPool(10);
    private MerkleHelman merkleHelman = new MerkleHelman();
    private ArrayList<ClientHandler>clientHandlers= new ArrayList<>();
    private Vector data;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> MassageList;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JButton jDeleteBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextAreaMessage;
    // End of variables declaration//GEN-END:variables
}



