// CatfoOD 2009-9-18 ����04:12:29

package jym.command.list;

import java.util.Iterator;

import jym.command.ICommander;
import jym.command.IContainer;
import jym.command.IPrinter;

public class SwitchLink implements ICommander {
	public final static String COMM = "sw";

	@Override
	public boolean doit(IContainer d, String[] args) {
		IPrinter ip = d.getPrinter();
		String name = null;
		if (args.length>1) {
			name = args[1];
		} else {
			name = getNext(d.getLogin().getLinkedNames(), 
					d.getCurrentLink().getName());
		}
		
		if ( !d.switchLink(name) ) {
			ip.pl( "[�����������" + name +"]" );
		} else {
			ip.pl( "[�л���: " + name + "]" );
		}
		
		return true;
	}
	
	private String getNext(Iterator<String> it, String currname) {
		String firstname = null;
		String nextname = null;
		
		if (it.hasNext()) {
			firstname = it.next();
			nextname = firstname;
		}
		
		boolean over = false;
		
		while (true) { 
			if (nextname.equals(currname)) {
				over = true;
			}
			if (it.hasNext()) {
				nextname = it.next();
				if (over) break;
			} else {
				nextname = firstname;
				break;
			}
		}
		
		return nextname;
	}

	@Override
	public String getCommandName() {
		return COMM;
	}

	@Override
	public String getHelp() {
		return "sw [linkname] - �л���linknameָ��������\n" +
				"\t\t �����ṩ����,���л�����һ������";
	}

	@Override
	public String getInfo() {
		return "�л����õ����ݿ�����";
	}

}
