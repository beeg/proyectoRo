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
	            tratarMedidas(lMedidas),
	            new String [] {
	                "Fecha", "Hora", "Latitud", "Longitud", "Valor"
	            }
	        ) {
	            Class[] types = new Class [] {
	                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
	
public String[][] tratarMedidas(ArrayList<String>medidas)	{
	String[][] m = new String[medidas.size()][5];
	medidas.remove(medidas.size()-1);
	medidas.remove(0);
	
	for(int j=0;j<medidas.size();j++)	{
		String cadena=medidas.get(j);	
		String[] sCadena = cadena.split(";");
		String f=sCadena[0];
		String h=sCadena[1];
		String coor=sCadena[2];
		String[] sCoor = coor.split("-");
		String lat=sCoor[0];
		String log=sCoor[1];
		String v=sCadena[3];
		for(int i=0;i<5;i++)	{			
			switch(i)	{
			case 0: //Fecha		
				m[j][i]=f; break;
			case 1:	//Hora
				m[j][i]=h; break;
			case 2: //Latitud
				m[j][i]=lat; break;
			case 3: //Longitud
				m[j][i]=log; break;
			case 4: //Valor
				m[j][i]=v; break;
			}
		}
	}	
	return m;
}
}
