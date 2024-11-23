package swing;

import data.DataFileServer;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CellEditorFile extends DefaultCellEditor {

    public CellEditorFile() {
        super(new JCheckBox());
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        Object data = jtable.getValueAt(i, 0);
        if (data instanceof DataFileServer) { // 만약 DataFileServer 객체라면 DataFileServer 객체의 getItem() 메서드를 호출하여 PanelStatus_Item 표시
            DataFileServer d = (DataFileServer) data;
            return d.getItem();
        } else {
            return super.getTableCellEditorComponent(jtable, o, bln, i, i1);
        }
    }
}
