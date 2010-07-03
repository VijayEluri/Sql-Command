// CatfoOD 2009-9-22 ����07:20:37

package jym.command.list;

import jym.command.ICommander;
import jym.command.IContainer;

public class DoCmdAllLink implements ICommander {

	@Override
	public boolean doit(IContainer d, String[] args) {
		if (args.length>1) {
			String[] ns = new String[args.length-1];
			for (int i=0; i<ns.length; ++i) {
				ns[i] = args[i+1];
			}
			DoSqlAllLink.doAllLinkCommand(d, ns);
			return true;
		}
		return false;
	}

	@Override
	public String getCommandName() {
		return "dc";
	}

	@Override
	public String getHelp() {
		return "dc <cmd> - �����п��õ�����������ִ������";
	}

	@Override
	public String getInfo() {
		return "�����п��õ�����������ִ������";
	}

}
