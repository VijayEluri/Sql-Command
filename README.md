﻿# Java Jdbc Command

加瓦:)数据库控制台，作者：CatfoOD  
本程序为开源程序，可以任意修改，并使用在任何程序中


connect.ini为配置文件，控制台首先加载配置文件的用户和配置登录数据库  
如果登陆失败，用户需要手动输入相关的配置


# connect.ini文件配置说明

connects		连接分组，每个分组都有以下字段  
		控制台启动后会尝试连接全部分组，之后通过命令切换分组

* _name		登陆数据库的用户名
* _pass		登陆数据库的密码
* _address	数据库的网络地址
* _port		数据库Tcp/Ip端口号
* _servername	数据库的实例名
* _type		数据库类型



双击 'start.cmd' 启动程序  
输入 help 命令可以得到帮助

** 每条命令结尾使用';'