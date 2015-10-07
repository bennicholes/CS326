// Ben Nicholes 
// 5/5/15

import java.io.*; 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class ColorSampler extends JFrame{

    // protected so other classes within ColorSampler can access it
    protected JLabel redLabel, greenLabel, blueLabel;

    protected JTextField redField, greenField, blueField;

    protected JButton save, reset, 
                      plusRed, minusRed,
                      plusBlue, minusBlue,
                      plusGreen, minusGreen;

    protected JList colorList;
    protected drawColor colorDisplay;

    protected int colorIndex;

    // default  
    protected int redBase = 255;
    protected int greenBase = 255;
    protected int blueBase = 255;
    
    String title = "Color Sampler";
    String titleChanged = title + "*";
    int numColors = 0;
    String colorNames[]; 

    // hold colors in list
    public class colorObject{

        public String colorName;
        public int red, blue, green;
    }

    protected colorObject[] colorArray = new colorObject[16];

    public static void main( String args[] ){

        // instantiate class
        new ColorSampler( "Color Sampler");
    }

    public ColorSampler( String title){

        // intialize window
        super(title);
        setSize(480, 510);       

        // allocate
        initializeGUI();

        // set gui
        setGUI();

        // get file
        try{
        readFile( colorArray );
        }

        catch (IOException e) 
        {
            // TODO Auto-generated catch block
                System.out.println("error");
                System.exit(0);
        }

        colorNames = new String[ numColors ];

        for( int i = 0; i < numColors; i++ ){

            colorNames[ i ] = colorArray[ i ].colorName;
        }

        colorList.setListData( colorNames );

        colorList.setSelectedIndex( 0 );
        
        redBase = colorArray[ 0 ].red;
        greenBase = colorArray[ 0 ].green;
        blueBase = colorArray[ 0 ].blue;

        redField.setText( redBase + "" );
        blueField.setText( blueBase + "" );
        greenField.setText( greenBase + "" );

        repaint();

        colorList.setLocation(370, 8);
        colorList.setSize(70, 250);
        
        colorDisplay.setLocation( 10, 10 );
        colorDisplay.setSize( 350, 260 );

        // set handlers
        setHandlers();

        setVisible(true);
    }


    public void initializeGUI(){

                // allocate buttons and fields
        redLabel = new JLabel( "Red: " );
        greenLabel = new JLabel( "Green: " );
        blueLabel = new JLabel( "Blue: ");

        redField = new JTextField();
        redField.setEditable( false );
        greenField = new JTextField();
        greenField.setEditable( false );
        blueField = new JTextField();
        blueField.setEditable( false );

        plusRed = new JButton( " + " );
        plusGreen = new JButton( " + ");
        plusBlue = new JButton( " + ");

        minusRed = new JButton( " - ");
        minusGreen = new JButton( " - ");
        minusBlue =  new JButton( " - ");

        save = new JButton( " Save " );
        reset = new JButton( " Reset ");

        colorDisplay = new drawColor();

        colorList = new JList();

    }

    public void setGUI(){

        getContentPane().setLayout( null );
        
        getContentPane().add( redLabel );
        getContentPane().add( greenLabel );
        getContentPane().add( blueLabel );

        getContentPane().add( redField );
        getContentPane().add( greenField );
        getContentPane().add( blueField );

        getContentPane().add( plusRed );
        getContentPane().add( plusBlue );
        getContentPane().add( plusGreen );

        getContentPane().add( minusRed );
        getContentPane().add( minusGreen );
        getContentPane().add( minusBlue );

        getContentPane().add( save );
        getContentPane().add( reset );

        getContentPane().add( colorDisplay );
        getContentPane().add( colorList );



        // set bounds
        redLabel.setLocation( 10, 275);
        redLabel.setSize( 55, 55 );
        greenLabel.setLocation( 10, 325);
        greenLabel.setSize( 55, 55 );
        blueLabel.setLocation( 10, 375);
        blueLabel.setSize( 55, 55 );
        
        redField.setLocation( 60, 280 );
        redField.setSize( 100, 40 );
        greenField.setLocation( 60, 330 );
        greenField.setSize( 100, 40 );        
        blueField.setLocation( 60, 380 );
        blueField.setSize( 100, 40 );
        

        plusRed.setLocation( 170, 280 );
        plusRed.setSize( 150, 40 );
        plusBlue.setLocation( 170, 380 );
        plusBlue.setSize( 150, 40 );
        plusGreen.setLocation( 170, 330 );
        plusGreen.setSize( 150, 40 );
                
        minusRed.setLocation(325, 280);
        minusRed.setSize(150 ,40);
        minusGreen.setLocation(325, 330);
        minusGreen.setSize(150 ,40);
        minusBlue.setLocation(325, 380);
        minusBlue.setSize(150 ,40);

        save.setLocation( 5, 430 );
        save.setSize( 235, 50 );
        reset.setLocation( 245, 430 );
        reset.setSize( 235, 55 );
    }

    public void setHandlers(){

        addWindowListener( new destroyWindow() );

        colorList.addListSelectionListener( new ListHandler() );
        
        save.addActionListener( new ActionHandler() );
        reset.addActionListener( new ActionHandler() );

        plusRed.addActionListener( new ActionHandler(){

            public void actionPerformed( ActionEvent event){
                    
                if( redBase < 255 ){

                    redBase += 5;
                    redField.setText( redBase + "" );

                    repaint();
                    ColorSampler.this.setTitle(titleChanged);
                }
            }
        });

        plusGreen.addActionListener( new ActionHandler(){

            public void actionPerformed( ActionEvent event){
                    
                if( greenBase < 255 ){

                    greenBase += 5;
                    greenField.setText( greenBase + "" );

                    repaint();
                    ColorSampler.this.setTitle(titleChanged);                    
                }
            }
        });

        plusBlue.addActionListener( new ActionHandler(){

            public void actionPerformed( ActionEvent event){
                    
                if( blueBase < 255 ){

                    blueBase += 5;
                    blueField.setText( blueBase + "" );

                    repaint();
                    ColorSampler.this.setTitle(titleChanged);
                }
            }            
        });

        minusRed.addActionListener( new ActionHandler(){

            public void actionPerformed( ActionEvent event){

                if( redBase > 0 ){

                    redBase -= 5;
                    redField.setText( redBase + "" );

                    repaint(); 
                    ColorSampler.this.setTitle(titleChanged);
                }

            }

        });

        minusGreen.addActionListener( new ActionHandler(){

            public void actionPerformed( ActionEvent event){

                if( greenBase > 0 ){

                    greenBase -= 5;
                    greenField.setText( greenBase + "" );

                    repaint(); 
                    ColorSampler.this.setTitle(titleChanged);
                }

            }

        });

        minusBlue.addActionListener( new ActionHandler(){


            public void actionPerformed( ActionEvent event){

                if( blueBase > 0 ){

                    blueBase -= 5;
                    blueField.setText( blueBase + "" );

                    repaint(); 
                    ColorSampler.this.setTitle(titleChanged);
                }

            }
        });

    }

    private class destroyWindow extends WindowAdapter{

        public void windowClosing( WindowEvent event){
            
                try{
                    FileOutputStream ostream = new FileOutputStream("input.txt");
                    PrintWriter writer = new PrintWriter(ostream);
                    for( int i = 0; i < numColors; i++ ){
                
                        writer.println( colorArray[i].colorName + " " + colorArray[i].red + " " + colorArray[i].green + " " + colorArray[i].blue);
                    }
                
                    writer.flush();
                    ostream.close();
                }
        

                catch ( IOException exception ) {
                    System.out.println("Error");
                }

                System.exit(0);
        }

        
    }

    private class ActionHandler implements ActionListener{

        public void actionPerformed( ActionEvent event ){

            // if save
            if( event.getSource() == save ){

                int i = colorList.getSelectedIndex();
                colorArray[ i ].red = redBase;
                colorArray[ i ].green = greenBase;
                colorArray[ i ].blue = blueBase;

                ColorSampler.this.setTitle(title);
            }

            // if reset
            if( event.getSource() == reset ){

                ColorSampler.this.setTitle(title);
                int i = colorList.getSelectedIndex();
                redBase = colorArray[i].red;
                greenBase = colorArray[i].green;
                blueBase = colorArray[i].blue;
                
                redField.setText( redBase + "");
                greenField.setText( greenBase + "");
                blueField.setText( blueBase + "");

                
                repaint();
            }

            repaint();


        }
    }

    private class ListHandler implements ListSelectionListener{

        public void valueChanged( ListSelectionEvent event ){
            if( event.getSource() == colorList ){

                if( !event.getValueIsAdjusting() ){
                    ColorSampler.this.setTitle(title);
                    int i = colorList.getSelectedIndex();
                    String temp = colorNames[ i ];
                    
                    redBase = colorArray[ i ].red;
                    redField.setText( redBase + "" );

                    greenBase = colorArray[ i ].green;
                    greenField.setText( greenBase + "" );

                    blueBase = colorArray[ i ].blue;
                    blueField.setText( blueBase + "" );

                    repaint();
                }
            }
        }
    }

    public class drawColor extends JComponent{

        public void paint( Graphics g){

            Dimension d = getSize();

            g.setColor( new Color( redBase, greenBase, blueBase, 255 ) );
            g.fillRect( 0, 0, d.width-2, d.height-2 );
        }
    }

    public void readFile( colorObject[] array ) throws IOException{

        FileInputStream stream = new FileInputStream("input.txt");  
        InputStreamReader reader = new InputStreamReader(stream); 
        StreamTokenizer tokens = new StreamTokenizer(reader); 
        
        while( tokens.nextToken() != tokens.TT_EOF ) {
            
            array[ numColors ] = new colorObject();
            
            array[ numColors ].colorName = ( String ) tokens.sval;
            tokens.nextToken();
            array[ numColors ].red = ( int ) tokens.nval;
            tokens.nextToken();
            array[ numColors ].green = ( int ) tokens.nval;
            tokens.nextToken();
            array[ numColors ].blue = ( int ) tokens.nval;
            
            numColors++;
        }
        
        stream.close();
    }


}



