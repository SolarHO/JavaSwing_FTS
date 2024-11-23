package swing;

import data.DataReader;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class CellEditor extends DefaultCellEditor { //CellEditor 클래스를 선언하고, DefaultCellEditor를 상속

    public CellEditor() {
        super(new JCheckBox());  //생성자에서는 JCheckBox를 사용하여 부모 클래스의 생성자호출
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        Object data = jtable.getValueAt(i, 0);
        if (data instanceof DataReader) { //이 메서드에서는 해당 셀의 데이터(DataReader 객체)를 확인하고, DataReader의 getStatus 메서드가 반환하는 컴포넌트(PanelStatus.java를 편집 컴포넌트로 사용) 
            DataReader reader = (DataReader) data;
            return reader.getStatus();
        } else {
            return super.getTableCellEditorComponent(jtable, o, bln, i, i1);
        }//
    }
}
