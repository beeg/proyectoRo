package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class VentanaMenu extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1669040666506804464L;
	private javax.swing.JTextField tID;
	private javax.swing.JLabel lIdentificador;
	private javax.swing.JButton bListSensor;
	private javax.swing.JButton bHistorico;
	private javax.swing.JButton bOnSensor;
	private javax.swing.JButton bOffSensor;
	private javax.swing.JButton bValAct;
	private javax.swing.JButton bOnGps;
	private javax.swing.JButton bOffGps;
	private javax.swing.JButton bFoto;
	private javax.swing.JButton bSalir;
	private javax.swing.JLabel lMensaje;
	private Cliente c;

	public VentanaMenu(final Cliente c) {
		this.c = c;
		// Inicializacion componentes
		lIdentificador = new javax.swing.JLabel();
		tID = new javax.swing.JTextField();
		bListSensor = new javax.swing.JButton();
		bHistorico = new javax.swing.JButton();
		bValAct = new javax.swing.JButton();
		bOnGps = new javax.swing.JButton();
		bOffGps = new javax.swing.JButton();
		bFoto = new javax.swing.JButton();
		bSalir = new javax.swing.JButton();
		lMensaje = new javax.swing.JLabel();
		bOnSensor = new javax.swing.JButton();
		bOffSensor = new javax.swing.JButton();

		lIdentificador.setText("Introduce el identificador:");
		bListSensor.setText("Listado de Sensores");
		bHistorico.setText("Historico");
		bValAct.setText("Valores Actuales");
		bOnGps.setText("ON GPS");
		bOffGps.setText("OFF GPS");
		bFoto.setText("Foto");
		bSalir.setText("Salir");
		bOnSensor.setText("ON Sensor");
		bOffSensor.setText("OFF Sensor");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(129, 129, 129)
								.addComponent(lIdentificador)
								.addGap(30, 30, 30)
								.addComponent(tID,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										79,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup()
																.addContainerGap(
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		bSalir))
												.addGroup(
														javax.swing.GroupLayout.Alignment.LEADING,
														layout.createSequentialGroup()
																.addGap(54, 54,
																		54)
																.addComponent(
																		bListSensor)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		66,
																		Short.MAX_VALUE)
																.addComponent(
																		bHistorico)
																.addGap(53, 53,
																		53)
																.addComponent(
																		bValAct)
																.addGap(46, 46,
																		46))
												.addGroup(
														layout.createSequentialGroup()
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(88,
																										88,
																										88)
																								.addComponent(
																										bOnGps)
																								.addGap(101,
																										101,
																										101)
																								.addComponent(
																										bFoto))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(77,
																										77,
																										77)
																								.addComponent(
																										bOffSensor)))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						bOnSensor)
																				.addComponent(
																						bOffGps))
																.addGap(58, 58,
																		58)))
								.addContainerGap())
				.addComponent(lMensaje,
						javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(bSalir)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addGap(11, 11,
																		11)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						lIdentificador)
																				.addComponent(
																						tID,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE))
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(80,
																										80,
																										80)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.BASELINE)
																												.addComponent(
																														bOffGps)
																												.addComponent(
																														bFoto)
																												.addComponent(
																														bOnGps)))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(31,
																										31,
																										31)
																								.addGroup(
																										layout.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.BASELINE)
																												.addComponent(
																														bValAct)
																												.addComponent(
																														bHistorico)
																												.addComponent(
																														bListSensor))))
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		75,
																		Short.MAX_VALUE)
																.addComponent(
																		lMensaje,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		17,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						bOffSensor)
																				.addComponent(
																						bOnSensor))
																.addGap(43, 43,
																		43)))));

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		// Listeners
		bListSensor.addActionListener(this);
		bHistorico.addActionListener(this);
		bOnSensor.addActionListener(this);
		bOffSensor.addActionListener(this);
		bValAct.addActionListener(this);
		bOnGps.addActionListener(this);
		bOffGps.addActionListener(this);
		bFoto.addActionListener(this);
		bSalir.addActionListener(this);

		// Para cuando le da a la x se comporte como si fuese el boton cancelar
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (c != null) {
					try {
						c.SALIR();
					} catch (IOException e1) {
						System.out.println("Error al salir");
						e1.printStackTrace();
					}
				}
				dispose();
			}
		});

		pack();
	}

	public void actionPerformed(ActionEvent a) {
		Object o = a.getSource();
		if (o == bListSensor) {
			ArrayList<String> sensores = c.listSensor();
			for (int i = 0; i < sensores.size(); i++) {
				System.out.println(sensores.get(i));
			}
			VentanaListSensor v = new VentanaListSensor(sensores);
			v.setVisible(true);
		} else if (o == bHistorico) {
			ArrayList<String> medidas = c.historico(tID.getText());
			if (medidas.get(0).contains("113")) { // Correcto
				VentanaHistorico v = new VentanaHistorico(medidas);
				v.setVisible(true);
			} else if (medidas.get(0).contains("414")) {
				lMensaje.setText("414 ERR Sensor desconocido");
			} else if (medidas.get(0).contains("415")) {
				lMensaje.setText("415 ERR Falta parámetro id_sensor");
			}
		} else if (o == bOnSensor) {
			String resultado = c.onSensor(tID.getText());
			lMensaje.setText(resultado);
		} else if (o == bOffSensor) {
			String resultado = c.offSensor(tID.getText());
			lMensaje.setText(resultado);
		} else if (o == bValAct) {
			String resultado = c.getValAct(tID.getText());
			lMensaje.setText(resultado);
		} else if (o == bFoto) {

		} else if (o == bOnGps) {
			String resultado = c.ONGPS();
			lMensaje.setText(resultado);
		} else if (o == bOffGps) {
			String resultado = c.OFFGPS();
			lMensaje.setText(resultado);
		} else if (o == bSalir) {
			try {
				c.SALIR();
			} catch (IOException e) {
				System.out.println("Error al salir");
				e.printStackTrace();
			}
			this.dispose();
		}
	}

	public static void main(String[] args) {
		// VentanaMenu v = new VentanaMenu();
		// v.setVisible(true);
	}

}
