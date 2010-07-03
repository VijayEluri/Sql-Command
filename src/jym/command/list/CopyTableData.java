// CatfoOD 2009-9-22 ����03:31:40

package jym.command.list;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jym.command.ICommander;
import jym.command.IContainer;
import jym.command.IPrinter;
import jym.command.Login;
import jym.jdbc.SqlLinked;

public class CopyTableData implements ICommander {
	public static final String COMM = "cpda";

	@Override
	public boolean doit(IContainer d, String[] args) {
		Login login = d.getLogin();
		IPrinter ip = d.getPrinter();
		if (args.length==3) {
			try {
				new CopyData(ip, login, args);
				return true;
			} catch (Exception e) {
				ip.pl(e.getMessage());
			}
		}
		return false;
	}

	@Override
	public String getCommandName() {
		return COMM;
	}

	@Override
	public String getHelp() {
		return COMM + 
				" <Ŀ������>.<Ŀ�����> <Դ����>.<Դ����>\n" +
				"\t ����Դ������������е�Ŀ���,ֻ���벻�޸�\n" +
				"\t ע���ṹ������ͬ\n" +
				"\t *������ƹ����г���Լ����ͻ,���ƵĽ�����ض�DBMS��ʵ���й�\n" +
				"\t *��ʱӦ��鸴�ƽ��";
	}

	@Override
	public String getInfo() {
		return "���Ʊ�����";
	}

}

class CopyData {
	private SqlLinked srclink;
	private SqlLinked dsclink;
	private IPrinter ip;
	
	protected CopyData(IPrinter ip, Login login, String[] args) {
		Pack dsc = new Pack(args[1]);
		Pack src = new Pack(args[2]);
		srclink = login.getLink(src.linkname);
		dsclink = login.getLink(dsc.linkname);
		checkNull(srclink, "��Ч��������:"+src.linkname);
		checkNull(dsclink, "��Ч��������:"+dsc.linkname);
		this.ip = ip;
		start(dsc, src);
	}
	
	private void start(Pack dsc, Pack src) {
		String errStr = "";
		ResultSet srcset = null;
		PreparedStatement dscset = null;
		
		try {
			ip.pl("��ʼ��...");
			errStr = "ȡ��Դ�����ݳ���";
			srcset = srclink.exe("select * from " + src.tabname);
			errStr = "ȡ��Դ��ṹ����";
			String sql = getInsertSql(srcset, dsc.tabname);
			errStr = "����Ŀ������";
			dscset = dsclink.getConnection().prepareStatement(sql);
			
			ip.pl("��ʼ����...");
			copy(dscset, srcset);
			
		} catch (SQLException e) {
			ip.pl(errStr + " " + e.getMessage());
		} finally {
			SqlLinked.close(srcset);
			SqlLinked.close(dscset);
		}
	}
	
	private void copy(PreparedStatement dsc, ResultSet src) throws SQLException {
		try {
		src.setFetchSize(1000); } catch (Exception e) {}
		ResultSetMetaData rsm = src.getMetaData();
		int colc = rsm.getColumnCount();
		
		int row = 0;
		try {
			while (src.next()) {
				for (int i=1; i<=colc; ++i) {
					dsc.setObject(i, src.getObject(i));
				}
				dsc.addBatch();
				row++;
			}
			dsc.executeBatch();
			ip.pl("���Ƴɹ�,��������"+row+"������");
		} catch (Exception e) {
			ip.pl("����δ��ȫ�ɹ�:" + e.getMessage());
		}
	}
	
	private String getInsertSql(ResultSet r, String dscTableName) throws SQLException {
		String sql = "insert into %1$s (%2$s) values (%3$s)";
		StringBuilder names = new StringBuilder();
		StringBuilder valsig= new StringBuilder();
		getTableNames(r, names, valsig);
		
		return String.format( 
				sql, dscTableName, names.toString(), valsig.toString() );
	}
		
	private void getTableNames(ResultSet r, StringBuilder names, StringBuilder sig) 
	throws SQLException {
		ResultSetMetaData rsm = r.getMetaData();
		for (int i=1; i<=rsm.getColumnCount(); ++i) {
			names.append(rsm.getColumnName(i));
			sig.append('?');
			if (i<rsm.getColumnCount()) {
				names.append(',');
				sig.append(',');
			}
		}
	}
	
	public static final void checkNull(Object o, String text) {
		if (o==null)
			throw new NullPointerException(text);
	}
	
	private class Pack { 
		String linkname;
		String tabname;
		Pack(String s) {
			String[] p = s.split("\\.");
			linkname = p[0];
			tabname = p[1];
		}
	}
}

