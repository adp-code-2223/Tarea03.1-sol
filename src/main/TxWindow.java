package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;
import modelo.Departamento;

import modelo.servicio.AccountServicio;
import modelo.servicio.DepartamentoServicio;
import modelo.servicio.IAccountServicio;
import modelo.servicio.IDepartamentoServicio;
import exceptions.InstanceNotFoundException;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.Dimension;

public class TxWindow extends JFrame {

	private JPanel contentPane;

	private JTextArea mensajes_text_Area;
	private JList JListAllDepts;

	private IDepartamentoServicio departamentoServicio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TxWindow frame = new TxWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TxWindow() {

		departamentoServicio = new DepartamentoServicio();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 772);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(8, 8, 821, 500);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 264, 669, 228);
		panel.add(scrollPane);

		mensajes_text_Area = new JTextArea();
		scrollPane.setViewportView(mensajes_text_Area);
		mensajes_text_Area.setEditable(false);
		mensajes_text_Area.setText("Panel de mensajes");
		mensajes_text_Area.setForeground(new Color(255, 0, 0));
		mensajes_text_Area.setFont(new Font("Monospaced", Font.PLAIN, 13));

		JButton btnShowAllDepts = new JButton("Mostrar departamentos");

		btnShowAllDepts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnShowAllDepts.setBounds(50, 37, 208, 36);
		panel.add(btnShowAllDepts);
		// panel.add(JListAllDepts);

		JScrollPane scrollPanel_in_JlistAllDepts = new JScrollPane();
		// scrollPane_1.setBounds(284, 237, 505, -200);
		panel.add(scrollPanel_in_JlistAllDepts);

		JListAllDepts = new JList();
		panel.add(JListAllDepts);

		JListAllDepts.setBounds(403, 37, 377, 200);

		JButton btnCrearNuevoDepartamento = new JButton("Crear nuevo departamento");
		btnCrearNuevoDepartamento.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				  JFrame owner = (JFrame) SwingUtilities.getRoot((Component)e.getSource());
				   CreateNewDeptDialog createDialog = new CreateNewDeptDialog(owner,Dialog.ModalityType.DOCUMENT_MODAL);
				   createDialog.setVisible(true);
				
			}
		});
		btnCrearNuevoDepartamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCrearNuevoDepartamento.setBounds(50, 85, 208, 36);
		panel.add(btnCrearNuevoDepartamento);
		// scrollPanel_in_JlistAllDepts.setViewportView(JListAllDepts);

		ActionListener showAllDepartamentosActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Departamento> departamentos = departamentoServicio.getAll();
				DefaultListModel<Departamento> defModel = new DefaultListModel<>();

				defModel.addAll(departamentos);
				

				JListAllDepts.setModel(defModel);
				addMensaje(true, "Se han recuperado: " + departamentos.size() + " departamentos");
			}
		};
		btnShowAllDepts.addActionListener(showAllDepartamentosActionListener);

	}

	private void addMensaje(boolean keepText, String msg) {
		String oldText = "";
		if (keepText) {
			oldText = mensajes_text_Area.getText();

		}
		oldText = oldText + "\n" + msg;
		mensajes_text_Area.setText(oldText);

	}
}
