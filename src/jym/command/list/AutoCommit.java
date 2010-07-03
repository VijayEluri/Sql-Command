// CatfoOD 2009-9-22 ����03:38:04

package jym.command.list;

import java.sql.Connection;
import java.sql.SQLException;

import jym.command.ICommander;
import jym.command.IContainer;
import jym.command.IPrinter;
import jym.jdbc.SqlLinked;

public class AutoCommit implements ICommander {

	@Override
	public boolean doit(IContainer d, String[] args) {
		IPrinter ip = d.getPrinter();
		SqlLinked link = d.getCurrentLink();
		Connection conn = link.getConnection();
		
		try {
			if (args.length>1) {
				switchAuto(args[1], ip, conn);
			} else {
				disp(ip, conn);
			}
		} catch (Exception e) {
			ip.pl(e.getMessage());
		}
		return true;
	}
	
	protected void switchAuto(String sw, IPrinter ip, Connection conn) throws SQLException {
		boolean auto = sw.equalsIgnoreCase("on");
		conn.setAutoCommit(auto);
		if (auto) {
			ip.pl("[����Ϊ�Զ��ݽ�ģʽ]");
		} else {
			ip.pl("[����Ϊ�ֶ��ݽ�ģʽ]");
		}
	}
	
	protected void disp(IPrinter ip, Connection conn) throws SQLException {
		if (conn.getAutoCommit()) {
			ip.pl("[��ǰΪ�Զ��ݽ�ģʽ]");
		} else {
			ip.pl("[��ǰΪ�ֶ��ݽ�ģʽ]");
		}
	}

	@Override
	public String getCommandName() {
		return "acom";
	}

	@Override
	public String getHelp() {
		return "acom [off/on]\n" +
				"\t ��/�ر��Զ��ݽ�,�޲�����ʾ��ǰ״̬";
	}

	@Override
	public String getInfo() {
		return "�л������Զ��ݽ���״̬";
	}

}
