// CatfoOD 2009-9-19 ����08:51:25

package jym.command.list;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jym.command.ICommander;
import jym.command.IContainer;
import jym.command.IPrinter;
import jym.command.dbtype.DBCommandTypeFactory;
import jym.command.dbtype.IDBCommand;
import jym.command.dbtype.IListCommand;
import jym.helper.FormatOut;
import jym.helper.TableFormatOut;
import jym.jdbc.SqlLinked;

public class List implements ICommander {
	
	@Override
	public boolean doit(IContainer d, String[] args) {
		IPrinter ip = d.getPrinter();
		boolean result = false;
		
		if (args.length>1) {
			if (args[1].equalsIgnoreCase("ct")) {
				
				if (args.length>2) {
					result = true;
					new PrintTableStruct(ip, d.getCurrentLink(), args[2]);
				}
				
			} else {
				
				IListCommand listd = getCmd(d);
		
				if (listd!=null) {
					result = true;
					
					if (args[1].equalsIgnoreCase("db")) {
						d.doSql( listd.listDBName() );
					}
					else if (args[1].equalsIgnoreCase("tb")) {
						d.doSql( listd.listTableName() );	
					} else {
						result = false;
					}
					
				} else {
					ip.pl("[��ǰ���ݿⲻ֧���������]");
					result = true;
				}
			}
		}
		return result;
	}
	
	private IListCommand getCmd(IContainer d) {
		SqlLinked sql = d.getCurrentLink();
		String type = sql.getDBtype();
		IDBCommand idbc = DBCommandTypeFactory.getCommand(type);
		if (idbc instanceof IListCommand) {
			return (IListCommand) idbc;
		}
		return null;
	}

	@Override
	public String getCommandName() {
		return "list";
	}

	@Override
	public String getInfo() {
		return "��ӡ���ݿ�����������";
	}

	@Override
	public String getHelp() {
		return "list <type> - ��ӡһ������typeָ��\n" +
				"\t<type> ȡֵ:\n" +
				"\t\t db - ��ӡȫ�����ݿ���\n" +
				"\t\t tb - ��ӡ��ǰ���ݿ����\n" +
				"\t\t ct <tablename> - ��ӡָ����ı�ṹ";
	}

}

class PrintTableStruct {
	private final String[] h = new String[] {
		"[ColumnName]", "[Type]", "[Precision]", "[Not Null]", "[AutoIncrement]"	
	};
	private TableFormatOut out;
	
	protected PrintTableStruct(IPrinter ip, SqlLinked link, String tablename) {
		ResultSet rs = null;
		try {
			rs = link.exe("select * from " + tablename);
			try { rs.setFetchSize(1);
			} catch (Exception e) {}
			ResultSetMetaData rsm = rs.getMetaData();
			
			out = new TableFormatOut(h.length);
			printHead();
			printColumn(rsm);
			out.print(ip);
		} catch (SQLException e) {
			ip.pl("[��ӡ��ṹ����:]" + e.getMessage());
		} finally {
			SqlLinked.close(rs);
		}
	}
	
	private void printColumn(ResultSetMetaData rsm) throws SQLException {
		int c = rsm.getColumnCount();
		for (int i=1; i<=c; ++i) {
			out.put( rsm.getColumnName(i) );
			out.put( rsm.getColumnTypeName(i) );
			out.put( rsm.getPrecision(i) );
			out.put( parseNull(rsm.isNullable(i)) );
			out.put( rsm.isAutoIncrement(i) );
		}
	}
	
	private String parseNull(int i) {
		String str = null;
		switch (i) {
		case ResultSetMetaData.columnNoNulls:
			str = "Yes";
			break;
		case ResultSetMetaData.columnNullable:
			str = "No";
			break;
		case ResultSetMetaData.columnNullableUnknown:
		default:
			str = "Unknown";
			break;
		}
		return str;
	}
	
	private void printHead() {
		for (int i=0; i<h.length; ++i) {
			out.put(h[i]);
		}
		for (int i=0; i<h.length; ++i) {
			out.put( "-" );
		}
	}
}

