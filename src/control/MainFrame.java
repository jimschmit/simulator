/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import model.World;
import model.deserializer.CellDeserializer;
import model.deserializer.PointDeserializer;
import model.deserializer.WorldDeserializer;
import model.entity.Cell;
import model.entity.Virus;

/**
 *
 * @author Jim_Laptop
 */
public class MainFrame extends javax.swing.JFrame
{
    private World world = new World(50, 50);
    Timer timer;
    /**
     * Creates new form MainFrame
     */
    public MainFrame()
    {
        initComponents();
        drawPanel1.setWorld(world);
        timer =  new Timer((100), new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                //stop timer when whether no virus or antivirus left
                if(world.getAntivirus().size() == 0 && world.getVirus().size() == 0) timer.stop();
                world.nextStep();
                world.commit();
                repaint();
            }
        });
    }
    
    /**
     *
     * @param json
     * @throws Exception
     */
    public void sendingPostRequest(String json) throws Exception {  
        String url = "http://www.qar.help/developping/upload.php";  
        URL obj = new URL(url);  
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();  

              // Setting basic post request  
        con.setRequestMethod("POST");  
        String postJsonData = "data="+ /*URLEncoder.encode(*/json/*, "UTF-8")*/;

        // Send post request  
        con.setDoOutput(true);  
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream()))
        {
            wr.writeBytes(postJsonData);
            wr.flush();  
        }
        
        int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
        con.disconnect();
 }  
    
    /**
     *
     * @param json
     * @throws Exception
     */
    public String gettingPostRequest() throws Exception {  
        String url = "http://www.qar.help/developping/download.php";  
        URL obj = new URL(url);  
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();  

              // Setting basic post request  
        con.setRequestMethod("POST");  
        String postJsonData = "data=1";

        // Send post request  
        con.setDoOutput(true);  
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream()))
        {
            wr.writeBytes(postJsonData);
            wr.flush();  
        }
        
        int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
//		System.out.println(response.toString());
                con.disconnect();
                return response.toString();
 }  
    
    
    /**
     *saving local json of world model
     */
    public void saveToFile()
    {
        try (PrintWriter out = new PrintWriter(new FileWriter("world.json")))
        {
            out.write(world.getJson());
        }
        catch (IOException ex)
        {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *load model from local json
     */
    public void loadFromFile(String json)
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(World.class, new WorldDeserializer());
        gsonBuilder.registerTypeAdapter(Cell.class, new CellDeserializer());
        gsonBuilder.registerTypeAdapter(Point.class, new PointDeserializer());

        Gson gson = gsonBuilder.create();
        System.out.println(json);
        world = gson.fromJson(json, World.class);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        startButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        drawPanel1 = new view.DrawPanel();
        nextStepButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        speedSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        colTextField = new javax.swing.JTextField();
        rowTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setText("start");
        startButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startButtonActionPerformed(evt);
            }
        });

        resetButton.setText("reset");
        resetButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout drawPanel1Layout = new javax.swing.GroupLayout(drawPanel1);
        drawPanel1.setLayout(drawPanel1Layout);
        drawPanel1Layout.setHorizontalGroup(
            drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawPanel1Layout.setVerticalGroup(
            drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        nextStepButton.setText("Next step");
        nextStepButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nextStepButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveButtonActionPerformed(evt);
            }
        });

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                loadButtonActionPerformed(evt);
            }
        });

        speedSlider.setMaximum(999);
        speedSlider.setValue(500);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                speedSliderStateChanged(evt);
            }
        });

        jLabel1.setText("Speed");

        colTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                colTextFieldActionPerformed(evt);
            }
        });

        rowTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rowTextFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Rows");

        jLabel3.setText("Columns");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(drawPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(startButton)
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(colTextField)
                        .addComponent(resetButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nextStepButton)
                                .addGap(37, 37, 37)
                                .addComponent(saveButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel2)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(speedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(jLabel1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(startButton)
                            .addComponent(resetButton)
                            .addComponent(nextStepButton)
                            .addComponent(saveButton)
                            .addComponent(loadButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(drawPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startButtonActionPerformed
    {//GEN-HEADEREND:event_startButtonActionPerformed
        timer.start();
        repaint();
    }//GEN-LAST:event_startButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_resetButtonActionPerformed
    {//GEN-HEADEREND:event_resetButtonActionPerformed
        timer.stop();
        world.alCells.clear();
        repaint();
    }//GEN-LAST:event_resetButtonActionPerformed

    private void nextStepButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nextStepButtonActionPerformed
    {//GEN-HEADEREND:event_nextStepButtonActionPerformed
        world.nextStep();
        world.commit();
        repaint();
    }//GEN-LAST:event_nextStepButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveButtonActionPerformed
    {//GEN-HEADEREND:event_saveButtonActionPerformed
        saveToFile();
        try
        {
            sendingPostRequest(world.getJson());
            System.out.println("sended");
        }
        catch (Exception ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.stop();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loadButtonActionPerformed
    {//GEN-HEADEREND:event_loadButtonActionPerformed
        try {
            loadFromFile(gettingPostRequest());
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        timer.stop();
        drawPanel1.setWorld(world);
        repaint();
    }//GEN-LAST:event_loadButtonActionPerformed

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_speedSliderStateChanged
    {//GEN-HEADEREND:event_speedSliderStateChanged
        timer.setDelay(1000 - speedSlider.getValue());
    }//GEN-LAST:event_speedSliderStateChanged

    private void rowTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rowTextFieldActionPerformed
    {//GEN-HEADEREND:event_rowTextFieldActionPerformed
       world = new World(Integer.valueOf(colTextField.getText()), Integer.valueOf(rowTextField.getText()));
       drawPanel1.setWorld(world);
       repaint();
    }//GEN-LAST:event_rowTextFieldActionPerformed

    private void colTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_colTextFieldActionPerformed
    {//GEN-HEADEREND:event_colTextFieldActionPerformed
        world = new World(Integer.valueOf(colTextField.getText()), Integer.valueOf(rowTextField.getText()));
        drawPanel1.setWorld(world);
        repaint();
    }//GEN-LAST:event_colTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField colTextField;
    private view.DrawPanel drawPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton nextStepButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JTextField rowTextField;
    private javax.swing.JButton saveButton;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
