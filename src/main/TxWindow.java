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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class TxWindow extends JFrame {

	private JPanel contentPane;

	private JTextArea mensajes_text_Area;
	private JList JListAllDepts;

	private IDepartamentoServicio departamentoServicio;
	private CreateNewDeptDialog createDialog;
	private JButton btnModificarDepartamento;
	private JButton btnEliminarDepartamento;

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

		btnModificarDepartamento = new JButton("Modificar departamento");
		btnModificarDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIx = JListAllDepts.getSelectedIndex();
				if (selectedIx > -1) {
					Departamento departamento = (Departamento) JListAllDepts.getModel().getElementAt(selectedIx);
					if (departamento != null) {
						
						JFrame owner = (JFrame) SwingUtilities.getRoot((Component) e.getSource());

						createDialog = new CreateNewDeptDialog(owner, "Modificar departamento",
								Dialog.ModalityType.DOCUMENT_MODAL, departamento);
						showDialog();
					}
				}
			}
		});

		JListAllDepts = new JList();
		JListAllDepts.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					int selectedIx = JListAllDepts.getSelectedIndex();
					btnModificarDepartamento.setEnabled((selectedIx > -1));
					btnEliminarDepartamento.setEnabled((selectedIx > -1));
					if (selectedIx > -1) {
						Departamento d = (Departamento) TxWindow.this.JListAllDepts.getModel().getElementAt(selectedIx);
//					if(d!=null){
						addMensaje(true, "Se ha seleccionado el d: " + d);
					}
				}
			}
		});
		JListAllDepts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		JListAllDepts.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int selectedIx = JListAllDepts.getSelectedIndex();
//				btnModificarDepartamento.setEnabled((selectedIx > -1));
//				btnEliminarDepartamento.setEnabled((selectedIx > -1));
//				if (selectedIx > -1) {
//					Departamento d = (Departamento) TxWindow.this.JListAllDepts.getModel().getElementAt(selectedIx);
////					if(d!=null){
//					addMensaje(true, "Se ha seleccionado el d: " + d);
//				}
//
////				int[] selectedIx = TxWindow.this.JListAllDepts.getSelectedIndices();
////				for (int i = 0; i < selectedIx.length; i++) {
////					Departamento d = (Departamento) TxWindow.this.JListAllDepts.getModel().getElementAt(selectedIx[i]);
////					if (d != null) {
////						addMensaje(true, "Se ha seleccionado el d: " + d);
////						btnModificarDepartamento.setEnabled(true);
////					} else {
////						btnModificarDepartamento.setEnabled(false);
////
////					}
////				}
////				if (selectedIx.length == 0) {
////					btnModificarDepartamento.setEnabled(false);
////
////				}
//
//			}
//		});
		panel.add(JListAllDepts);

		JListAllDepts.setBounds(403, 37, 377, 200);

		JButton btnCrearNuevoDepartamento = new JButton("Crear nuevo departamento");
		btnCrearNuevoDepartamento.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFrame owner = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
				createDialog = new CreateNewDeptDialog(owner, "Crear nuevo departamento",
						Dialog.ModalityType.DOCUMENT_MODAL, null);
				showDialog();
			}
		});
		btnCrearNuevoDepartamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCrearNuevoDepartamento.setBounds(50, 85, 208, 36);
		panel.add(btnCrearNuevoDepartamento);

		btnModificarDepartamento.setEnabled(false);
		btnModificarDepartamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnModificarDepartamento.setBounds(50, 139, 208, 36);
		panel.add(btnModificarDepartamento);

		btnEliminarDepartamento = new JButton("Eliminar departamento");
		btnEliminarDepartamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIx = JListAllDepts.getSelectedIndex();
				if (selectedIx > -1) {
					Departamento d = (Departamento) JListAllDepts.getModel().getElementAt(selectedIx);
					if (d != null) {
						try {
							boolean exito = departamentoServicio.delete(d.getDeptno());
							if (exito) {
								addMensaje(true, "Se ha eliminado el dept con id: " + d.getDeptno());
								getAllDepartamentos();
							}
						} catch (exceptions.InstanceNotFoundException e1) {
							addMensaje(true, "No se ha podido borrar el departamento. No se ha encontrado con id: "
									+ d.getDeptno());
						} catch (Exception ex) {
							addMensaje(true, "No se ha podido borrar el departamento. ");
							System.out.println("Exception: " + ex.getMessage());
							ex.printStackTrace();
						}
					}
				}
			}
		});
		btnEliminarDepartamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEliminarDepartamento.setEnabled(false);
		btnEliminarDepartamento.setBounds(50, 201, 208, 36);
		panel.add(btnEliminarDepartamento);
		// scrollPanel_in_JlistAllDepts.setViewportView(JListAllDepts);

		ActionListener showAllDepartamentosActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAllDepartamentos();
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

	private void showDialog() {
		createDialog.setVisible(true);
		Departamento departamentoACrear = createDialog.getResult();
		if (departamentoACrear != null) {

			saveOrUpdate(departamentoACrear);
		}
	}

	private void saveOrUpdate(Departamento dept) {
		try {
			Departamento nuevo = departamentoServicio.saveOrUpdate(dept);
			if (nuevo != null) {
				addMensaje(true, "Se ha creado un departamento con id: " + nuevo.getDeptno());
				getAllDepartamentos();
			} else {
				addMensaje(true, " El departamento no se ha creado/actualizado correctamente");
			}
			
		} catch (Exception ex) {
			addMensaje(true, "Ha ocurrido un error y no se ha podido crear el departamento");
		}
	}

	private void getAllDepartamentos() {
		List<Departamento> departamentos = departamentoServicio.getAll();
		addMensaje(true, "Se han recuperado: " + departamentos.size() + " departamentos");
		DefaultListModel<Departamento> defModel = new DefaultListModel<>();

		defModel.addAll(departamentos);

		JListAllDepts.setModel(defModel);

	}

}
