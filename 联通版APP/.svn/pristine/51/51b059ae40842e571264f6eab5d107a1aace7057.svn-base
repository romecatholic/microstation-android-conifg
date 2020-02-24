package com.dgm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataAssembleUtil {

	public static byte[] ADDR = { 0x30, 0x31 };
	public static byte[] VERSION = { 0x32, 0x30 };

	public static int ISZORC = 0;// 主从设备的选项，如果是从除第一个页面外不可进入系统。

	public static boolean setPriviledge = true;// 是否有设置权限

	public static volatile int FIRSTSET = 0;// 第一次设置时需要验证

	public static double latitude = 0.0, longitude = 0.0;// 存放经纬度
	
	//2019年9月24日删除密码样式
	public static boolean isSendPass=false;//是否发送过二次鉴权
	
	public static String DGMUUID="69400001-b5a3-f393-e0a9-e50e24dcca99";//大光明的特征号
	public static String DGMWRITE="69400002-b5a3-f393-e0a9-e50e24dcca99";//大光明的写特征号
	public static String DGMNOTIFY="69400003-b5a3-f393-e0a9-e50e24dcca99";//大光明的通知特征号

	public static String JAUUID="69400001-b5a3-f393-e0a9-e50e24dcca99";//嘉安的特征号
	public static String JAWRITE="69400002-b5a3-f393-e0a9-e50e24dcca99";//嘉安的写特征号
	public static String JANOTIFY="69400003-b5a3-f393-e0a9-e50e24dcca99";//嘉安的通知特征号
	
	public static int isJAUUID=0;//判断是否使用的是嘉安的模块 0-嘉安，1-大光明

	public byte[] setAddr(int add) {
		byte[] check_b0 = new byte[2];
		check_b0[0] = (byte) ((add & 0xf0) >> 4);
		check_b0[1] = (byte) (add & 0x0f);

		HexToAsc(check_b0, 2);
		return check_b0;

	}

	/**
	 * 组装读取数据指令
	 * 
	 * @param recByte
	 *            VER ,ADR,CID1数据包
	 * @param cid2
	 *            cid2
	 * @return
	 */
	public byte[] AssembleReadData(byte[] recByte, String cid2) {
		byte[] b_cid2 = cid2.getBytes();
		byte[] ver = new byte[] { recByte[0], recByte[1] };
		byte[] adr = new byte[] { recByte[2], recByte[3] };
		byte[] cid1 = new byte[] { recByte[4], recByte[5] };
		byte[] newB = new byte[12];// 除SOI、EOI和CHKSUM外的数据
		for (int j = 0; j < 12; j++) {
			if (j < 6) {
				newB[j] = recByte[j];
			} else {
				newB[j] = 0x30;
			}
		}
		newB[6] = b_cid2[0];
		newB[7] = b_cid2[1];
		byte[] dateB = new byte[newB.length + 4];
		for (int j = 0; j < newB.length; j++) {
			dateB[j] = newB[j];
		}

		byte[] checkByte = CheckSum_Add(newB, newB.length);// 校验位byte
		dateB[newB.length] = checkByte[0];
		dateB[newB.length + 1] = checkByte[1];
		dateB[newB.length + 2] = checkByte[2];
		dateB[newB.length + 3] = checkByte[3];

		byte[] sendDate = new byte[dateB.length + 2];

		for (int j = 1; j < dateB.length + 1; j++) {
			sendDate[j] = dateB[j - 1];
		}
		sendDate[0] = 0x7E;
		sendDate[dateB.length + 1] = 0x0D;
		// String s=new String (sendDate);
		return sendDate;
	}

	/**
	 * 组装读取数据指令
	 * 
	 * @param recByte
	 *            VER ,ADR,CID1数据包
	 * @param cid2
	 *            cid2
	 * @return
	 */
	public byte[] AssembleReadData2(byte[] recByte, String cid2) {
		byte[] b_cid2 = cid2.getBytes();
		byte[] ver = new byte[] { recByte[0], recByte[1] };
		byte[] adr = new byte[] { recByte[2], recByte[3] };
		byte[] cid1 = new byte[] { recByte[4], recByte[5] };
		byte[] newB = new byte[16];// 除SOI、EOI和CHKSUM外的数据
		for (int j = 0; j < 16; j++) {
			if (j < 6) {
				newB[j] = recByte[j];
			} else {
				newB[j] = 0x30;
			}
		}
		newB[6] = b_cid2[0];
		newB[7] = b_cid2[1];
		newB[8] = 0x43;
		newB[9] = 0x30;
		newB[10] = 0x30;
		newB[11] = 0x32;
		byte[] dateB = new byte[newB.length + 4];
		for (int j = 0; j < newB.length; j++) {
			dateB[j] = newB[j];
		}

		byte[] checkByte = CheckSum_Add(newB, newB.length);// 校验位byte
		dateB[newB.length] = checkByte[0];
		dateB[newB.length + 1] = checkByte[1];
		dateB[newB.length + 2] = checkByte[2];
		dateB[newB.length + 3] = checkByte[3];

		byte[] sendDate = new byte[dateB.length + 2];

		for (int j = 1; j < dateB.length + 1; j++) {
			sendDate[j] = dateB[j - 1];
		}
		sendDate[0] = 0x7E;
		sendDate[dateB.length + 1] = 0x0D;
		// String s=new String (sendDate);
		return sendDate;
	}

	/**
	 * 组装读取数据指令
	 * 
	 * @param recByte
	 *            VER ,ADR,CID1数据包
	 * @param cid2
	 *            cid2
	 * @return
	 */
	public byte[] AssembleReadData3(byte[] recByte, String cid2) {
		byte[] b_cid2 = cid2.getBytes();
		byte[] ver = new byte[] { recByte[0], recByte[1] };
		byte[] adr = new byte[] { recByte[2], recByte[3] };
		byte[] cid1 = new byte[] { recByte[4], recByte[5] };
		byte[] newB = new byte[14];// 除SOI、EOI和CHKSUM外的数据
		for (int j = 0; j < 12; j++) {
			if (j < 6) {
				newB[j] = recByte[j];
			} else {
				newB[j] = 0x30;
			}
		}
		newB[6] = b_cid2[0];
		newB[7] = b_cid2[1];
		newB[12] = 0x46;
		newB[13] = 0x46;
		byte[] dateB = new byte[newB.length + 4];
		for (int j = 0; j < newB.length; j++) {
			dateB[j] = newB[j];
		}

		byte[] checkByte = CheckSum_Add(newB, newB.length);// 校验位byte
		dateB[newB.length] = checkByte[0];
		dateB[newB.length + 1] = checkByte[1];
		dateB[newB.length + 2] = checkByte[2];
		dateB[newB.length + 3] = checkByte[3];

		byte[] sendDate = new byte[dateB.length + 2];

		for (int j = 1; j < dateB.length + 1; j++) {
			sendDate[j] = dateB[j - 1];
		}
		sendDate[0] = 0x7E;
		sendDate[dateB.length + 1] = 0x0D;
		// String s=new String (sendDate);
		return sendDate;
	}

	/*******************************************************************************
	 * Function Name : CheckSum_Add Description : 计算尾部校验数据 Input : 需要转换的数组
	 * *check_buf,u16 len,数组长度 len Output : None Return : None
	 *******************************************************************************/
	public byte[] CheckSum_Add(byte[] check_buf, int len) {
		int i;
		int sum = 0;
		byte[] check_b0 = new byte[4];
		for (i = 0; i < len; i++) {
			sum += check_buf[i];
		}
		sum = ~(sum % 0x10000) + 1;
		check_b0[0] = (byte) ((sum & 0xf000) >> 12);
		check_b0[1] = (byte) ((sum & 0x0f00) >> 8);
		check_b0[2] = (byte) ((sum & 0x00f0) >> 4);
		check_b0[3] = (byte) (sum & 0x000f);

		HexToAsc(check_b0, 4);
		return check_b0;
	}

	/*******************************************************************************
	 * Function Name : HexToAsc Description : 16进制转换成ascii码 Input : 需要转换的数组
	 * *hex,u16 len,数组长度 len Output : None Return : None
	 *******************************************************************************/
	private void HexToAsc(byte[] hex, int len) {
		int i;
		for (i = 0; i < len / 2; i++) {
			if (hex[2 * i] > 9) {
				hex[2 * i] += 55;
			} else {
				hex[2 * i] += 48;
			}
			if (hex[2 * i + 1] > 9) {
				hex[2 * i + 1] += 55;
			} else {
				hex[2 * i + 1] += 48;
			}
		}
	}

	/**
	 * 设置指令数据包组装
	 * 
	 * @param recByte2
	 * @param cid2
	 * @param sendByte
	 * @return
	 */
	public byte[] sendSetCmdA016(byte[] recByte2, String cid2, byte[] sendByte) {
		byte[] recByte = new byte[recByte2.length + 2];
		for (int j = 0; j < recByte2.length; j++) {
			recByte[j] = recByte2[j];
		}
		byte[] b_cid2 = cid2.getBytes();
		recByte[recByte2.length] = b_cid2[0];
		recByte[recByte2.length + 1] = b_cid2[1];
		byte[] newB = new byte[12 + sendByte.length];// 除SOI、EOI和CHKSUM外的数据
		for (int j = 0; j < 8; j++) {
			newB[j] = recByte[j];
		}
		// byte[] lengthByte = getDataLength(sendByte.length);
		byte[] lengthByte = { 0x41, 0x30, 0x31, 0x36 };
		// LENGTH数据
		for (int j = 8; j < 12; j++) {
			newB[j] = lengthByte[j - 8];
		}
		// INFO数据
		for (int j = 12; j < sendByte.length + 12; j++) {
			newB[j] = sendByte[j - 12];
		}
		byte[] checkByte = CheckSum_Add(newB, newB.length);// 校验位byte
		byte[] dateB = new byte[newB.length + 4];
		for (int j = 0; j < newB.length; j++) {
			dateB[j] = newB[j];
		}
		dateB[newB.length] = checkByte[0];
		dateB[newB.length + 1] = checkByte[1];
		dateB[newB.length + 2] = checkByte[2];
		dateB[newB.length + 3] = checkByte[3];

		byte[] sendDate = new byte[dateB.length + 2];

		for (int j = 1; j < dateB.length + 1; j++) {
			sendDate[j] = dateB[j - 1];
		}
		sendDate[0] = 0x7E;
		sendDate[dateB.length + 1] = 0x0D;
		return sendDate;
	}

	/**
	 * 设置指令数据包组装
	 * 
	 * @param recByte2
	 * @param cid2
	 * @param sendByte
	 * @return
	 */
	public byte[] sendSetCmd(byte[] recByte2, String cid2, byte[] sendByte) {
		byte[] recByte = new byte[recByte2.length + 2];
		for (int j = 0; j < recByte2.length; j++) {
			recByte[j] = recByte2[j];
		}
		byte[] b_cid2 = cid2.getBytes();
		recByte[recByte2.length] = b_cid2[0];
		recByte[recByte2.length + 1] = b_cid2[1];
		byte[] newB = new byte[12 + sendByte.length];// 除SOI、EOI和CHKSUM外的数据
		for (int j = 0; j < 8; j++) {
			newB[j] = recByte[j];
		}
		byte[] lengthByte = getDataLength(sendByte.length);
		// LENGTH数据
		for (int j = 8; j < 12; j++) {
			newB[j] = lengthByte[j - 8];
		}
		// INFO数据
		for (int j = 12; j < sendByte.length + 12; j++) {
			newB[j] = sendByte[j - 12];
		}
		byte[] checkByte = CheckSum_Add(newB, newB.length);// 校验位byte
		byte[] dateB = new byte[newB.length + 4];
		for (int j = 0; j < newB.length; j++) {
			dateB[j] = newB[j];
		}
		dateB[newB.length] = checkByte[0];
		dateB[newB.length + 1] = checkByte[1];
		dateB[newB.length + 2] = checkByte[2];
		dateB[newB.length + 3] = checkByte[3];

		byte[] sendDate = new byte[dateB.length + 2];

		for (int j = 1; j < dateB.length + 1; j++) {
			sendDate[j] = dateB[j - 1];
		}
		sendDate[0] = 0x7E;
		sendDate[dateB.length + 1] = 0x0D;
		return sendDate;
	}

	/*******************************************************************************
	 * Function Name : getDataLength Description : 计算LENGTH数据格式，并转成对应格式 Output :
	 * None Return : None
	 *******************************************************************************/
	public byte[] getDataLength(int lengthID2) {

		String lengthLck = LengthChkSum(lengthID2);// 校验码LCHKSUM
		String lengthHex = String.format("%02x", lengthID2);// 长度标示码LENID
		// LENGTH数据组装，长度标示码LENID不足三位前边补0
		if (lengthHex.length() < 3) {
			if (lengthHex.length() == 2) {
				lengthHex = "0" + lengthHex;
			} else if (lengthHex.length() == 1) {
				lengthHex = "00" + lengthHex;
			}
		}
		int lengthInt = Integer.valueOf(lengthLck + lengthHex, 16);
		byte[] b = intToAscByte4(lengthInt);
		return b;
	}

	/**
	 * 计算Length的校验位数据
	 * 
	 * @param s
	 * @return
	 */
	public static String LengthChkSum(int s) {
		int x = 16 - (((s / 0x10) / 16 + (s / 0x10) % 16 + s % 0x10) % 16);
		return String.format("%02x", x);// 2表示需要两个16进行数;
	}

	/**
	 * int数据转4位ascii数据
	 * 
	 * @param idata
	 * @return
	 */
	public byte[] intToAscByte4(int idata) {
		byte[] check_b0 = new byte[4];
		check_b0[0] = (byte) ((idata & 0xf000) >> 12);
		check_b0[1] = (byte) ((idata & 0x0f00) >> 8);
		check_b0[2] = (byte) ((idata & 0x00f0) >> 4);
		check_b0[3] = (byte) (idata & 0x000f);

		HexToAsc(check_b0, 4);
		return check_b0;
	}

	public byte[] intToAscByte2(int idata) {
		byte[] check_b0 = new byte[2];
		check_b0[0] = (byte) ((idata & 0xf0) >> 4);
		check_b0[1] = (byte) (idata & 0x0f);

		HexToAsc(check_b0, 2);
		return check_b0;
	}

	/**
	 * long数据转4位ascii数据
	 * 
	 * @param idata
	 * @return
	 */
	public byte[] LongToAscByte4(long idata) {
		byte[] check_b0 = new byte[4];
		check_b0[0] = (byte) ((idata & 0xf000) >> 12);
		check_b0[1] = (byte) ((idata & 0x0f00) >> 8);
		check_b0[2] = (byte) ((idata & 0x00f0) >> 4);
		check_b0[3] = (byte) (idata & 0x000f);

		HexToAsc(check_b0, 4);
		return check_b0;
	}

	public byte[] longToAscByte2(long idata) {
		byte[] check_b0 = new byte[2];
		check_b0[0] = (byte) ((idata & 0xf0) >> 4);
		check_b0[1] = (byte) (idata & 0x0f);

		HexToAsc(check_b0, 2);
		return check_b0;
	}

	/**
	 * 接收到数据校验和判断
	 * 
	 * @param cmd
	 * @return
	 */
	public boolean ifCheckSum(String cmd) {

		byte[] b = hex2byte(cmd);
		byte[] noCheckSum = new byte[b.length - 4];
		byte[] checkByte = new byte[4];
		checkByte[0] = b[b.length - 4];
		checkByte[1] = b[b.length - 3];
		checkByte[2] = b[b.length - 2];
		checkByte[3] = b[b.length - 1];
		for (int i = 0; i < noCheckSum.length; i++) {
			noCheckSum[i] = b[i];
		}
		byte[] jisuanCheckByte = CheckSum_Add(noCheckSum, noCheckSum.length);
		String s1 = new String(jisuanCheckByte);
		String s2 = new String(checkByte);
		if (s1.compareTo(s2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/*******************************************************************************
	 * Function Name : Data_Close Description : 将数据合并 Input : 需要转换的数组
	 * *data_bef,u16 len,数组长度 len_bef Output : None Return : None
	 *******************************************************************************/
	public byte[] Data_Close(byte[] data_bef, int len_bef) {
		byte[] b = new byte[len_bef / 2];
		int i;
		int len_aft;
		byte data_bef0 = 0;
		byte data_bef1 = 0;
		for (i = 0; i < len_bef / 2; i++) {
			data_bef0 = (byte) (data_bef[i * 2] << 4);
			data_bef1 = data_bef[i * 2 + 1];
			b[i] = (byte) (data_bef0 | data_bef1);
			// *(data_bef + i) = (*(data_bef + i * 2) << 4) | *(data_bef + i * 2
			// + 1);
		}
		for (i = len_bef / 2; i < len_bef; i++) {
			data_bef[i] = 0;// *(data_bef + i) = 0;
		}
		len_aft = len_bef / 2;
		return b;
	}

	public void AscToHex2(byte[] asc, int len) {

		int i;
		for (i = 0; i < len / 2; i++) {
			if (asc[2 * i] > 'Z') {
				asc[2 * i] -= 87;
			} else {
				if (asc[2 * i] > '9') {
					asc[2 * i] -= 55;
				} else {
					asc[2 * i] -= 48;
				}
			}

			if (asc[2 * i + 1] > 'Z') {
				asc[2 * i + 1] -= 87;
			} else {
				if (asc[2 * i + 1] > '9') {
					asc[2 * i + 1] -= 55;
				} else {
					asc[2 * i + 1] -= 48;
				}
			}
		}
	}

	/**
	 * 十六进制串转化为byte数组
	 * 
	 * @return the array of byte
	 */
	public static final byte[] hex2byte(String hex)
			throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	/**
	 * 16进制字符串转ASCII码数组
	 * 
	 * @param idata2
	 * @return
	 */
	public byte[] stringHToAscByte2(String idata2) {
		byte[] check_b0 = new byte[2];
		int idata = Integer.parseInt(idata2, 16);
		check_b0[0] = (byte) ((idata & 0xf0) >> 4);
		check_b0[1] = (byte) (idata & 0x0f);

		HexToAsc(check_b0, 2);
		return check_b0;
	}

	/*
	 * public byte[] intToStringAscByte4(int idata) { String
	 * d=String.valueOf(idata); char[] ch=d.toCharArray(); //byte[] check_b0 =
	 * new byte[4]; //check_b0[0] = (byte)((idata & 0xf000) >> 12);
	 * //check_b0[1] = (byte)((idata & 0x0f00) >> 8); //check_b0[2] =
	 * (byte)((idata & 0x00f0) >> 4); //check_b0[3] = (byte)(idata & 0x000f);
	 * 
	 * //HexToAsc(check_b0, 4); byte[] check_b0 = new byte[4]; for(int
	 * i=0;i<ch.length;i++){ check_b0[i]=Integer.valueOf(ch[i]).byteValue(); }
	 * //HexToAsc(check_b0, 4); return check_b0; }
	 */

	public static int parseInt(byte high) {
		if ((high & 0x80) == 0x00) {
			return high & 0xFF;
		} else {
			return ((high & 0xFF) | 0xFFFFFF00);
		}
	}

	public static int parseInt(byte low, byte high) {
		if ((high & 0x80) == 0x00) {
			return (low & 0xFF | (high & 0xFF) << 8);
		} else {
			return ((low & 0xFF | (high & 0xFF) << 8) | 0xFFFF0000);
		}
	}

	public static int parseUnsignedInt(byte high) {
		if ((high & 0x80) == 0x00) {
			return high & 0xFF;
		} else {
			return ((high & 0xFF) & 0x000000FF);
		}
	}

	public static int parseUnsignedInt(byte low, byte high) {
		if ((high & 0x80) == 0x00) {
			return (low & 0xFF | (high & 0xFF) << 8);
		} else {
			return ((low & 0xFF | (high & 0xFF) << 8) & 0x0000FFFF);
		}
	}

	public static long parseUnsignedInt(byte b, byte c, byte d, byte e) {
		// if((b & 0x80)==0x00){
		return (e & 0xFF | (d & 0xFF) << 8 | (c & 0xFF) << 16 | (b & 0xFF) << 24);
		/*
		 * }else{ return ((e & 0xFF | (d & 0xFF) << 4 | (c & 0xFF) << 8| (b &
		 * 0xFF) << 12) | 0x00000000); }
		 */
	}

	public static long parseInt(byte b, byte c, byte d, byte e) {
		if ((b & 0x80) == 0x00) {
			return (e & 0xFF | (d & 0xFF) << 8 | (c & 0xFF) << 16 | (b & 0xFF) << 24);
		} else {
			return ((e & 0xFF | (d & 0xFF) << 8 | (c & 0xFF) << 16 | (b & 0xFF) << 24) | 0x00000000);
		}
	}

	public static int parseInt(byte b, byte c, byte d) {
		if ((b & 0x80) == 0x00) {
			return (d & 0xFF | (c & 0xFF) << 4 | (b & 0xFF) << 8);
		} else {
			return (d & 0xFF | (c & 0xFF) << 4 | (b & 0xFF) << 8) | 0xFF000000;
		}
	}

	public static boolean checkDouble(String str) {
		Boolean strResult = str.matches("-?[0-9]+.?[0-9]*");

		if (strResult == true) {
			System.out.println("Is Number!");
		} else {
			System.out.println("Is not Number!");
		}
		return strResult;
	}
	
	public static boolean verifyIP(String addr){
		if(addr.length() < 7 || addr.length() > 15 || "".equals(addr)){
			return false;
		}
		String rexp ="([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		//    String rexp ="([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(addr);
		boolean ipAddress = mat.find();
		if(ipAddress){
			String ips[] = addr.split("\\.");
			if(ips.length==4){
				try{
					for(String ip : ips){
						if(Integer.parseInt(ip)<0||Integer.parseInt(ip)>255){
							return false;
						}
					}
				}catch (Exception e){
					return false;
				}
				
				return true;
			}else{
				return false;
			}
		}
		return ipAddress;
	}


}
