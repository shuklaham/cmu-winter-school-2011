/*
   Manish Bansal
 */

package desktopapplication1;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.Arrays;

/**
 * The application's main frame.
 */
public class DesktopApplication1View extends FrameView {

    HashMap<String,String> auth_id_title;//has values separated by #.It does not begin with #.
    HashMap<String,Integer> auth_id_title_count;
    HashMap<String,String> pid_title;
    HashMap<String,String> pid_authors;
    HashMap<String,String> auth_id_cluster;
    HashMap<String,String> auth_id_pid;//has vlues separatedin with #.It does not begin with #.
    HashMap<String,String> cluster_auth_names_topic_text2 = new HashMap<String,String>();
    HashMap<String,String> cluster_auth_names_topic_text1 = new HashMap<String,String>();
    HashMap<String,String> cluster_auth_names_topic_text3 = new HashMap<String,String>();
    HashMap<String,String> pid_cit;

    public void initialise_maps()
    {        
        auth_id_title = new HashMap<String,String>();
        auth_id_title_count = new HashMap<String,Integer>();
        pid_title = new HashMap<String,String>();
        pid_authors = new HashMap<String,String>();
        auth_id_cluster = new HashMap<String,String>();
        auth_id_pid = new HashMap<String,String>();
        auth_id_title.clear();
        auth_id_cluster.clear();
        auth_id_pid.clear();
        auth_id_title_count.clear();
        pid_authors.clear();
        pid_title.clear();
        FileWriter fw = null ;
        FileReader fr = null ;
        BufferedReader bfr = null;
//        BufferedWriter bfw = null;
        FileReader fr_cluster = null;
        BufferedReader bfr_cluster_stream = null;

        auth_id_title.clear();
        auth_id_title_count.clear();

        pid_cit = new HashMap<String,String>();
        FileReader fr_cit = null ;
        BufferedReader bfr_cit = null;

        try
        {
           fr_cit = new FileReader("C:/Users/Shukla/Desktop/paper_citations.txt");
           bfr_cit = new BufferedReader(fr_cit);
        }//end of try
        catch(IOException ee)
        {
            System.out.println("csv file not found.where to read data from");
        }//end of catch
        String paper_cit_line = "";
        while(true)
        {
            try
            {
                paper_cit_line = bfr_cit.readLine();
                if(paper_cit_line == null)
                {
                    break;
                }
                String [] c_pid = paper_cit_line.split("\t");
                c_pid[1] = c_pid[1].substring(0,3) + c_pid[1].substring(4,8);
                pid_cit.put( c_pid[1] , c_pid[0] );
//              System.out.println(c_pid[1]+"   :"+c_pid[0]);
/*              System.out.println(paper_cit_line);
                System.out.println(paper_cit_line.length());
                paper_cit_line = paper_cit_line.trim();
                int index_space = paper_cit_line.indexOf('\t');
                System.out.println(index_space);
                String citation_val = paper_cit_line.substring(0,index_space);
                String pid_cit = paper_cit_line.substring(index_space);
                pid_cit = pid_cit.trim();
                pid_cit = pid_cit.substring(0,3) + pid_cit.substring(3,7);
                System.out.println(pid_cit + ":" + citation_val);
*/
            }
            catch(IOException ee)
            {
                System.out.println("problem in reading a line from paper -- citations");
            }
        }




        try
        {
        fr = new FileReader("C:/Users/Shukla/Desktop/filtered_allinone.csv");
        bfr = new BufferedReader(fr);

        fr_cluster = new FileReader("C:/Users/Shukla/Desktop/cluster_input.csv");
        bfr_cluster_stream = new BufferedReader(fr_cluster);

//        fw = new FileWriter("C:/Users/Manish/Desktop/author_wali_file.txt");
//        bfw = new BufferedWriter(fw);
        }//end of try
        catch(IOException e)
        {
            System.out.println("csv file not found.where to read data from");
        }//end of catch

        String line = "";
//        int county = 0;
        String cl_line = "";
        while(true)
        {
            try
            {
                  cl_line = bfr_cluster_stream.readLine();
                  if(cl_line == null)
                  {
                    break;
                  }
                  cl_line = cl_line.toUpperCase();
            }
            catch(IOException ee)
            {
                System.out.println("error in reading the cluster file");
            }

            String [] splitted = cl_line.split(",");
//            System.out.println(splitted[0]);
            auth_id_cluster.put(splitted[0].toUpperCase(),splitted[1].toUpperCase());
            auth_id_pid.put(splitted[0].toUpperCase(),"") ;
            auth_id_title.put(splitted[0].toUpperCase(),"");
            auth_id_title_count.put(splitted[0].toUpperCase(),0);
    //        county++;
        }
    //    System.out.println("total no of authors from clusters = "+county);

        


    //    int county1 = 0;
        while( true )
        {
           try
           {
              line = bfr.readLine();
              if(line == null)
              {
                    break;
              }
              line.toUpperCase();
           }
           catch(IOException ee)
           {
              System.out.println("ERROR IN READING A LINE");
           }
           String fields[] = {"","",""};
           String [] fields_label = {"PID","TITLE","AUTHORS"};
           int i = 1,j;
           int first_index_comma = -1,last_index_comma = -1;

           first_index_comma = line.indexOf(",");
           last_index_comma= line.lastIndexOf(",");
           fields[0]=line.substring(0,first_index_comma);
           i=1;
           if(last_index_comma != line.length()-1 )
           {
               fields[2] = line.substring(last_index_comma+1);
               line = line.substring(0,last_index_comma);//to remove comma before #
               i = 2;
               fields[1] = line.substring(first_index_comma + 1);
           }
           else
           {
               fields[1] = line.substring(first_index_comma + 1,last_index_comma);
           }
           String out;
           if(i==1) {
                continue;
            }
           pid_title.put( fields[0].toUpperCase() , fields[1].toUpperCase() );
           pid_authors.put( fields[0].toUpperCase() , fields[2].substring(1).toUpperCase() );
           fields[2] = fields[2].substring(1);
//           System.out.println("fields[0] :"+fields[0].toUpperCase()+"\nfields[1] :"+fields[1].toUpperCase()+"\nfields[2] :"+fields[2].toUpperCase());
           int len,k;
           String [] splitted_authors = new String[20];
           if( fields[2].indexOf('#') < 0 )
           {
               splitted_authors[0] = fields[2];
               len = 1;
           }
           else
           { 
               splitted_authors = fields[2].split("#");
               len = splitted_authors.length;
           }
           for( k = 0 ; k < len ; k++ )
           {
               String key = splitted_authors[k];
//             System.out.println("AUTHOR    :"+key);
               if ( auth_id_title.containsKey(key) && ( !( auth_id_title.get(key).equalsIgnoreCase("")) )  )
               {
                   String projects = auth_id_title.get( key );
                   projects = projects + " # " + fields[1];
                   auth_id_title.put(key.toUpperCase(), projects.toUpperCase()) ;
                   int count = auth_id_title_count.get(key);
                   count++;
                   auth_id_title_count.put(key.toUpperCase(),count);
                   String pid = auth_id_pid.get(key);
                   pid = pid+"#"+fields[0];
                   auth_id_pid.put(key.toUpperCase(),pid.toUpperCase());
               }
               else if( (auth_id_title.containsKey(key)) && (auth_id_title.get(key).equalsIgnoreCase("")) )
               {
                   auth_id_title.put(key.toUpperCase(),fields[1].toUpperCase());
                   auth_id_title_count.put(key.toUpperCase(),1);
                   auth_id_pid.put(key.toUpperCase(),fields[0].toUpperCase());
  //                 county1++;
               }

           }
       }    

//       System.out.println("no. of authors matching  :"+county1);

     String [] sort_str = new String[8000];
     int zzz = 0;
     for(String key : auth_id_cluster.keySet())
     {
         sort_str[zzz++] = key;
     }
     int no_of_keys = zzz;
     for(int index_var1 = 0; index_var1 < no_of_keys ; index_var1++)
     {
        for(int index_var2 = 0 ; index_var2 < no_of_keys ; index_var2++ )
        {
            if(sort_str[index_var1].compareTo(sort_str[index_var2]) < 0)
            {
                String temp = sort_str[index_var1];
                sort_str[index_var1] = sort_str[index_var2];
                sort_str[index_var2] = temp;
            }
        }
     }

     for(int var_1 = 0; var_1 < no_of_keys ; var_1++ )
         {
                String key = sort_str[var_1];
                System.out.println(key);
                if(key.length()>1)
                {
                   choice1.add(key.toUpperCase());
                   choice2.add(key.toUpperCase());
                }
 //                  System.out.println(key + "   " + auth_id_pid.get(key));
 //                  System.out.println(key + "   " + auth_id_title.get(key));
 //                  System.out.println(key + "   " + auth_id_title_count.get(key) + "\n");
         }
/*       for(String key : auth_id_cluster.keySet())
       {

            System.out.println(key.toUpperCase());
            System.out.println(auth_id_cluster.get(key).toUpperCase());

       }
*/
       choice1.setVisible(true);
       choice2.setVisible(true);
       choice3.setVisible(true);
       button2.setVisible(true);
    }

    public DesktopApplication1View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        initialise_maps();
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopApplication1.getApplication().getMainFrame();
            aboutBox = new DesktopApplication1AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication1.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        choice1 = new java.awt.Choice();
        choice2 = new java.awt.Choice();
        choice3 = new java.awt.Choice();
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        textArea3 = new java.awt.TextArea();
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        label6 = new java.awt.Label();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        list1 = new java.awt.List();

        mainPanel.setName("mainPanel"); // NOI18N

        choice1.setName("choice1"); // NOI18N

        choice2.setName("choice2"); // NOI18N

        choice3.setName("choice3"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getResourceMap(DesktopApplication1View.class);
        button1.setLabel(resourceMap.getString("button1.label")); // NOI18N
        button1.setName("button1"); // NOI18N
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        button2.setLabel(resourceMap.getString("button2.label")); // NOI18N
        button2.setName("button2"); // NOI18N
        button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button2ActionPerformed(evt);
            }
        });

        textArea3.setName("textArea3"); // NOI18N

        label1.setName("label1"); // NOI18N
        label1.setText(resourceMap.getString("label1.text")); // NOI18N

        label2.setName("label2"); // NOI18N
        label2.setText(resourceMap.getString("label2.text")); // NOI18N

        label3.setName("label3"); // NOI18N
        label3.setText(resourceMap.getString("label3.text")); // NOI18N

        label4.setName("label4"); // NOI18N
        label4.setText(resourceMap.getString("label4.text")); // NOI18N

        label5.setName("label5"); // NOI18N
        label5.setText(resourceMap.getString("label5.text")); // NOI18N

        label6.setName("label6"); // NOI18N
        label6.setText(resourceMap.getString("label6.text")); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addComponent(textArea3, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                        .addGap(106, 106, 106))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(choice3, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(label6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(choice1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                    .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(23, 23, 23)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(choice2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(label4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(105, 105, 105))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choice2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choice3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textArea3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication1.DesktopApplication1.class).getContext().getActionMap(DesktopApplication1View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        list1.setName("list1"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button2ActionPerformed

        String key = choice3.getSelectedItem();
        String label1_text = cluster_auth_names_topic_text1.get(key);
        String label2_text = cluster_auth_names_topic_text2.get(key);
        String label3_text = cluster_auth_names_topic_text3.get(key);
        String [] temp = key.split("  WITH  ");
//        System.out.println(temp[0]);
//        System.out.println(auth_id_title.get(temp[0]));
//        System.out.println(cluster_auth_names_topic_text2.get(key));
//       textArea2.setText(label1_text);
        if(label2_text == null)
            System.out.println("label2_text is null");
        System.out.println(label2_text);
        textArea3.setText(label2_text);
//       textArea4.setText(label3_text);
    }//GEN-LAST:event_button2ActionPerformed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed

        button2.setVisible(true);
        choice3.setVisible(true);
        textArea3.setText("");

        String  auth_1 = choice1.getSelectedItem();
        String  auth_2 = choice2.getSelectedItem();

//        String auth_1 = "bhiksha_raj" ;
//        String auth_2 = "carolyn_penstein_rose" ;

        auth_1.toUpperCase();
        auth_2.toUpperCase();

        cluster_auth_names_topic_text2.clear();
        cluster_auth_names_topic_text1.clear();
        cluster_auth_names_topic_text3.clear();
        choice3.removeAll();

        String cluster_1 = auth_id_cluster.get(auth_1).toUpperCase();
        String cluster_2 = auth_id_cluster.get(auth_2).toUpperCase();
        String [] cluster1_auths = new String[3000];
        String [] cluster2_auths = new String[3000];
        int cluster1_len = 0;
        int cluster2_len = 0;
        label1.setText(cluster_1);
        label2.setText(cluster_2);
        HashMap<String,String> temp_hashmap_for_clust1_auth_id_pid = new HashMap<String,String>();
        HashMap<String,String> temp_hashmap_for_clust2_auth_id_pid = new HashMap<String,String>();

        // above two hashmaps contain auth_names(id) v/s their pids separated by hash(#)
        for(String key : auth_id_cluster.keySet() )
        {

            if( auth_id_pid.get(key) == null || key == null ) {
                continue;
            }
            String cluster = auth_id_cluster.get(key).toUpperCase();
            if(cluster.equalsIgnoreCase(cluster_1))
            {
              temp_hashmap_for_clust1_auth_id_pid.put(key.toUpperCase(),auth_id_pid.get(key).toUpperCase());
              //  cluster1_auths[cluster1_len++] = key;
            }
            if(cluster.equalsIgnoreCase(cluster_2))
            {
              //  cluster2_auths[cluster2_len++] = key;
              temp_hashmap_for_clust2_auth_id_pid.put(key.toUpperCase(),auth_id_pid.get(key).toUpperCase());
            }
        }
        /*
        for(String key : pid_authors.keySet())
        {
          String auth_list = pid_authors.get(key).toUpperCase();
          System.out.println(auth_list);
        }
        */
        for( String clust1_key : temp_hashmap_for_clust1_auth_id_pid.keySet() )//clust1_key is author
        {
            String pids_1 = temp_hashmap_for_clust1_auth_id_pid.get(clust1_key).toUpperCase();
            String[] pid_clus_1 = new String[60];
            String[] pid_clus_2 = new String[60];
            int flen_1 = 0;
            int flen_2 = 0;
            if(pids_1.indexOf('#') > 0)
            {
               pid_clus_1 = auth_id_pid.get(clust1_key.toUpperCase()).split("#");
               flen_1 = auth_id_pid.get(clust1_key.toUpperCase()).split("#").length;
            }
            else
            {
                pid_clus_1[0] = clust1_key.toUpperCase();
                flen_1 = 1;
            }
            for(int z = 0 ; z < flen_1 ; z++)
            {
                String pre_pid = pid_clus_1[z].toUpperCase();
                String auth_list = pid_authors.get(pre_pid);
                if( auth_list == null ) {
                    continue;
                }
                auth_list = auth_list.toUpperCase();
            //    System.out.println(auth_list);
                String [] auth_arr = new String[10];
                int no_auth_arr = 0;
                if( auth_list.indexOf("#") > 0 )
                {
                        auth_arr = auth_list.toUpperCase().split("#");
                        no_auth_arr = auth_list.toUpperCase().split("#").length;
                }
                else
                {
                        auth_arr[0] = auth_list.toUpperCase();
                        no_auth_arr = 1;
                }
                String year_str = pre_pid.substring(1,3);
                int year_int = Integer.parseInt(year_str);
                if(year_int<12)
                        year_int = 2000 + year_int;
                else
                        year_int = 1900 + year_int;
                for( int y = 0 ; y < no_auth_arr ; y++ )
                {
                        if( auth_arr[y].equalsIgnoreCase(clust1_key) )
                        {
                        continue;
                        }


                        if(temp_hashmap_for_clust2_auth_id_pid.containsKey(auth_arr[y].toUpperCase()))
                        {
//                          System.out.println("#"+clust1_key + "#" + auth_arr[y]+"#");
                            String g1 = clust1_key + "  with  " + auth_arr[y];
                            g1 = g1.toUpperCase();
                            if( cluster_auth_names_topic_text2.containsKey(g1) )
                            {
                                  String g2 = cluster_auth_names_topic_text2.get(g1);
       //                         System.out.println(g1+ ":  " + pid_title.get(pid_clus_1[z]).toUpperCase() );
                                  String no_cit = pid_cit.get(pre_pid);
                                  int number_cit = 0;
                                  if(no_cit != null)
                                      number_cit = Integer.parseInt(no_cit);
                                      g2 = g2 + "\n" + pid_title.get(pid_clus_1[z].toUpperCase()) +" - "+ year_int + " - " + number_cit;
                                cluster_auth_names_topic_text2.put(g1,g2.toUpperCase());
                            }
                            else
                            {
                                String no_cit = pid_cit.get(pre_pid);
                                int number_cit = 0;
                                if(no_cit != null)
                                    number_cit = Integer.parseInt(no_cit);
                               cluster_auth_names_topic_text2.put(g1,pid_title.get(pid_clus_1[z]).toUpperCase()+" - "+year_int+" - "+number_cit);
                            }
                          //  System.out.println(g1+ "  " + pid_title.get(pid_clus_1[z]).toUpperCase() );
                        }
                }
            }

        }
/*
        for(String key : cluster_auth_names_topic_text2.keySet())
        {
                System.out.println(key + ":  " + cluster_auth_names_topic_text2.get(key));
        }
*/
/*
        LinkedList<String> str = new LinkedList<String>();
        for( String key : cluster_auth_names_topic_text2.keySet() )
        {
                System.out.println("begin  :" + key);
                String[] temp = key.split("  WITH  ");
                String rev;
                System.out.println("temp[0]"+temp[0]);
                System.out.println("temp[1]"+temp[1]);
                if(temp[0].equalsIgnoreCase(temp[1]))
                 {
                    str.add(key);
                    continue;
                 }
                rev = temp[1]+"  WITH  "+temp[0];
                if( cluster_auth_names_topic_text2.containsKey(rev) )
                 {
                    str.add(key);
                    continue;
                 }
                System.out.println("end  :"+key);
        }
     ListIterator<String> itr = str.listIterator();
     while(itr.hasNext())
     {
                cluster_auth_names_topic_text2.remove(itr.toString());
     }*/

     if(!cluster_auth_names_topic_text2.isEmpty())
     {

            for (String key : cluster_auth_names_topic_text2.keySet())
            {
                choice3.add(key);
            }
     }

    }//GEN-LAST:event_button1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.Button button2;
    private java.awt.Choice choice1;
    private java.awt.Choice choice2;
    private java.awt.Choice choice3;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.List list1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private java.awt.TextArea textArea3;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}