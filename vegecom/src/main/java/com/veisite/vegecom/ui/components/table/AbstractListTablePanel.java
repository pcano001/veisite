package com.veisite.vegecom.ui.components.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;
import org.jopendocument.dom.OOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.veisite.vegecom.VegecomException;
import com.veisite.vegecom.ui.components.panels.DefaultTableStatusBar;
import com.veisite.vegecom.export.tabular.TableModelExporter;

public abstract class AbstractListTablePanel<T> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(AbstractListTablePanel.class);
	
	protected AbstractListJTable<T> table;
	
	protected JPopupMenu popupMenu;
	private AbstractAction recargaDatos;
	private JMenu exportMenu;
	
	protected DefaultTableStatusBar statusBar;
	
	protected Component parent;

	/**
	 * Utilidad para fomateo
	 */
	private DecimalFormat df = new DecimalFormat();

	
	public AbstractListTablePanel(Component parent, AbstractListJTable<T> table) 
			throws  VegecomException {
		super();
		this.parent = parent;
		this.table = table;
		initComponent();
	}
	
	
	/**
	 * Metodo que se llama para inicializar los componentes
	 * Debe llamarse por las subclases
	 * @throws VegecomException 
	 */
	protected void initComponent() {
		setLayout(new BorderLayout());
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(table);
		add(sp,BorderLayout.CENTER);
		statusBar = new DefaultTableStatusBar();
		showStatusBar();
		
		table.getModel().addDataLoadListener(new DataLoadListener() {
			@Override
			public void dataLoadInit() {
				statusBar.startProgress();
				statusBar.setProgressText("Cargando ...");
			}
			@Override
			public void dataLoadEnd() {
				statusBar.stopProgress();
				statusBar.setProgressText("Total: "+df.format(table.getModel().getRowCount())+" reg.");
				updateStatusBar();
			}
			@Override
			public void dataLoadError(Throwable exception) {
				logger.error("Error en carga de datos",exception);
				statusBar.stopProgress();
				statusBar.setProgressText("Error ...");
				updateStatusBar();
			}
		});
		table.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				updateStatusBar();
			}
		});
		table.getModel().refreshData();
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				updateStatusBar();
			}
		});
		
		configureTable();
		configurePopupMenu();
		updateStatusBar();
	}


	public void updateStatusBar() {
		String label = df.format(table.getRowCount());
		label += " registros";
		int sl = table.getSelectedRowCount();
		if (sl != 0) {
			label += " (";
			label += df.format(sl) + " seleccionados)";
		}
		statusBar.setText(label);
	}
	
	/**
	 * Oculta la barra de estado.
	 */
	public void hideStatusBar() {
		remove(statusBar);
	}
	
	/**
	 * Muestra la barra de estado.
	 */
	public void showStatusBar() {
		add(statusBar,BorderLayout.SOUTH);
	}
	
	/**
	 * Metodo llamado cuando se hace doble click en la tabla.
	 */
	protected abstract void doubleClickOnTable();
	
	/**
	 * Metodo lanzado al evento de poppup
	 */
	protected void showTablePopupMenu(Point p) {
		if (popupMenu==null) return;
		if (popupMenu.getComponentCount()==0) return;
		/* Leventamos el menu de edición */
		/* Ponerlo de forma que no se oculte */
		Point ap = new Point(p);
		SwingUtilities.convertPointToScreen(ap,table);
		Dimension s = popupMenu.getPreferredSize();
		Dimension sS = Toolkit.getDefaultToolkit().getScreenSize();
		if (ap.x+s.width > sS.width) {
			ap.x = sS.width-s.width-1;
		}
		if (ap.y+s.height > sS.height-35) {
			ap.y = ap.y-s.height;
		}
		SwingUtilities.convertPointFromScreen(ap,table);
		enableDisablePopupMenu();
		popupMenu.show(table,ap.x,ap.y);
	}
	
	protected void enableDisablePopupMenu() {
		// Do nothing, override for customizing
	}
	
	protected void configureTable() {
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					doubleClickOnTable();
				}
			}
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					/* Seleccionar la fila donde el cursor */
					int row = table.rowAtPoint(e.getPoint());
					if (row != -1) {
						int sel[] = table.getSelectedRows();
						boolean esta = false;
						for (int j = 0; j < sel.length; j++)
							esta = esta || (sel[j] == row);
						if (!esta) {
							table.clearSelection();
							table.setRowSelectionInterval(row, row);
						}
					}
					showTablePopupMenu(e.getPoint());
				}
			}
			public void mouseReleased(MouseEvent e) {
			}
		});
	}
	
	protected void configurePopupMenu() {
		popupMenu = new JPopupMenu();
		if (table.getModel().isUpdateable()) {
			recargaDatos = new AbstractAction("Actualizar Datos") {
				private static final long serialVersionUID = 876879258255911796L;

				@Override
				public void actionPerformed(ActionEvent e) {
					table.getModel().refreshData();
				}
			};
			popupMenu.addSeparator();
			popupMenu.add(recargaDatos);
		}
		if (table.getModel().isExportable()) {
			exportMenu = new JMenu("Exportar datos");
			AbstractAction exportOds = new AbstractAction("a formato ODS") {
				private static final long serialVersionUID = 5011377808209087337L;

				@Override
				public void actionPerformed(ActionEvent e) {
					exportModel(TableModelExporter.ODS_FORMAT);
				}
			};
			AbstractAction exportXls = new AbstractAction("a formato XLS") {
				private static final long serialVersionUID = -4593381485553577032L;

				@Override
				public void actionPerformed(ActionEvent e) {
					exportModel(TableModelExporter.XLS_FORMAT);
				}
			};
			exportMenu.add(exportOds);
			exportMenu.add(exportXls);
			popupMenu.addSeparator();
			popupMenu.add(exportMenu);
		}
	}


	/**
	 * @return the table
	 */
	public AbstractListJTable<T> getTable() {
		return table;
	}


	/**
	 * @return the popupMenu
	 */
	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}


	/**
	 * @return the statusBar
	 */
	public DefaultTableStatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * Exportamos el modelo a hoja de calculo en fichero temporal
	 */
	private void exportModel(int format) {
		Cursor c = table.getCursor();
		table.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		try {
			TableModelExporter te = new TableModelExporter(format);
			TableViewModelWraper model = new TableViewModelWraper(table);
			File f = te.exportToTempFile(model, "gaslabexport");
			if (format==TableModelExporter.ODS_FORMAT) {
				OOUtils.open(f);
			}
			if (format==TableModelExporter.XLS_FORMAT) {
				Desktop.getDesktop().open(f);
			}
		} catch (Throwable t) {
			logger.error("Error al exportar datos.",t);
			ErrorInfo info = new ErrorInfo("Error al exportar datos", t.getMessage(), 
					t.getMessage(), null, t, null, null);
			JXErrorPane.showDialog(this, info);
		} finally {
			table.setCursor(c);
		}
	}

}
