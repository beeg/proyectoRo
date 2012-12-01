package Client;

import java.util.ArrayList;

import javax.swing.JFrame;

public class VentanaHistorico extends JFrame{
	
	private static final long serialVersionUID = -7928364815249010853L;
	private ArrayList<String> lMedidas;
	private javax.swing.JLabel lOK;
	private javax.swing.JLabel lFin;
	private javax.swing.JScrollPane sMedidas;
	private javax.swing.JTable tMedidas;

	public VentanaHistorico(ArrayList<String> lMedidas)	{
		 //Inicializar componentes
		 lOK = new javax.swing.JLabel();
	     sMedidas = new javax.swing.JScrollPane();
	     tMedidas = new javax.swing.JTable();
	     lFin = new javax.swing.JLabel();
	     lOK.setText("113 OK Lista de medidas");
	     lFin.setText("212 OK Lista Finalizada");
	     
	        tMedidas.setModel(new javax.swing.table.DefaultTableModel(
	            new Object [][] {
	                {null, null, null, null},
	                {null, null, null, null},
	                {null, null, null, null},
	                {null, null, null, null}
	            },
	            new String [] {
	                "Fecha", "Latitud", "Longitud", "Valor"
	            }
	        ) {
	            Class[] types = new Class [] {
	                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
	            };

	            public Class getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }
	        });
	        sMedidas.setViewportView(tMedidas);

	        

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(31, 31, 31)
	                        .addComponent(sMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(149, 149, 149)
	                        .addComponent(lFin))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(146, 146, 146)
	                        .addComponent(lOK)))
	                .addContainerGap(31, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(lOK)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(sMedidas, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(lFin)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	 
	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	        pack();
	}
}
