package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

public class VentanaMenu extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = -1669040666506804464L;
	private javax.swing.JPanel pFondo;
	private javax.swing.JLabel lOpciones;
	private javax.swing.JButton bListSensor;
	private javax.swing.JButton bHistorico;
	private javax.swing.JButton bOnSensor;
	private javax.swing.JButton bOffSensor;
	private javax.swing.JButton bGetValAct;
	private javax.swing.JButton bGetFoto;
	private javax.swing.JButton bSalir;
	private javax.swing.JLabel lMensaje;
	private Cliente c;
	
	public VentanaMenu(final Cliente c)	{
		this.c=c;
		//Inicializacion componentes
        pFondo = new javax.swing.JPanel();
        lOpciones = new javax.swing.JLabel();
        bListSensor = new javax.swing.JButton();
        bHistorico = new javax.swing.JButton();
        bOnSensor = new javax.swing.JButton();
        bOffSensor = new javax.swing.JButton();
        bGetValAct = new javax.swing.JButton();
        bGetFoto = new javax.swing.JButton();
        bSalir = new javax.swing.JButton();
        lMensaje = new javax.swing.JLabel();

        lOpciones.setText("Selecciona una de las siguientes opciones:");
        bListSensor.setText("Listado de sensores");
        bHistorico.setText("Histórico");
        bOnSensor.setText("Sensor ON");
        bOffSensor.setText("Sensor OFF");
        bGetValAct.setText("Valores Actuales");
        bGetFoto.setText("Foto");
        bSalir.setText("Salir");
        lMensaje.setText("");
       
        javax.swing.GroupLayout pFondoLayout = new javax.swing.GroupLayout(pFondo);
        pFondo.setLayout(pFondoLayout);
        pFondoLayout.setHorizontalGroup(
            pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bSalir)
                .addContainerGap())
            .addGroup(pFondoLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lOpciones)
                    .addGroup(pFondoLayout.createSequentialGroup()
                        .addGroup(pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bListSensor)
                            .addGroup(pFondoLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(bOnSensor)))
                        .addGroup(pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pFondoLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(bHistorico)
                                .addGap(51, 51, 51)
                                .addComponent(bGetValAct))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pFondoLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(bGetFoto)
                                .addGap(73, 73, 73)
                                .addComponent(bOffSensor)))))
                .addContainerGap(76, Short.MAX_VALUE))
            .addComponent(lMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pFondoLayout.setVerticalGroup(
            pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bSalir)
                .addGap(3, 3, 3)
                .addComponent(lOpciones)
                .addGap(30, 30, 30)
                .addGroup(pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bListSensor)
                    .addComponent(bHistorico)
                    .addComponent(bGetValAct))
                .addGap(18, 18, 18)
                .addGroup(pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bOnSensor)
                    .addComponent(bOffSensor)
                    .addComponent(bGetFoto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(lMensaje))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pFondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        
        //Listeners
        bListSensor.addActionListener(this);
        bHistorico.addActionListener(this);
        bOnSensor.addActionListener(this);
        bOffSensor.addActionListener(this);
        bGetValAct.addActionListener(this);
        bGetFoto.addActionListener(this);
        bSalir.addActionListener(this);
        
		// Para cuando le da a la x se comporte como si fuese el boton cancelar
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(c!=null){
					c.SALIR();
				}
				dispose();
			}
		});

        pack();
	}

	public void actionPerformed(ActionEvent a) {
		Object o = a.getSource();
		if(o==bListSensor){
			ArrayList<String>sensores=c.listSensor();
			for(int i=0;i<sensores.size();i++){				
				System.out.println(sensores.get(i));
			}
			VentanaListSensor v = new VentanaListSensor(sensores);
			v.setVisible(true);
		}else if(o==bHistorico){
			
		}else if(o==bOnSensor){
			
		}else if(o==bOffSensor){
			
		}else if(o==bGetValAct){
			
		}else if(o==bGetFoto){
			
		}else if(o==bSalir){
			c.SALIR();
			this.dispose();
		}
	}
	
	public static void main (String[] args)	{
		//VentanaMenu v = new VentanaMenu();
		//v.setVisible(true);
	}

}
