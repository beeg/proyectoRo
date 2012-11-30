package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private Cliente c;
	public VentanaMenu(Cliente c)	{
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

        lOpciones.setText("Selecciona una de las siguientes opciones:");
        bListSensor.setText("Listado de sensores");
        bHistorico.setText("Histórico");
        bOnSensor.setText("Sensor ON");
        bOffSensor.setText("Sensor OFF");
        bGetValAct.setText("Valores Actuales");
        bGetFoto.setText("Foto");
        bSalir.setText("Salir");

        javax.swing.GroupLayout pFondoLayout = new javax.swing.GroupLayout(pFondo);
        pFondo.setLayout(pFondoLayout);
        pFondoLayout.setHorizontalGroup(
            pFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addGroup(pFondoLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(bHistorico)
                                .addGap(51, 51, 51)
                                .addComponent(bGetValAct)
                                .addContainerGap(76, Short.MAX_VALUE))
                            .addGroup(pFondoLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(bGetFoto)
                                .addGap(73, 73, 73)
                                .addComponent(bOffSensor)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bSalir)
                .addContainerGap())
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
                .addContainerGap(29, Short.MAX_VALUE))
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
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pack();
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	public static void main (String[] args)	{
		//VentanaMenu v = new VentanaMenu();
		//v.setVisible(true);
	}

}
