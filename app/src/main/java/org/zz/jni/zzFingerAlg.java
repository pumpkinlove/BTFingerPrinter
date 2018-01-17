package org.zz.jni; 
public class zzFingerAlg{

	static {
		System.loadLibrary("TMF20Alg");
	}

	/**
	 * @author   chen.gs
	 * @category Get algorithm version
	 * @param    szVersion  - Version information(128bytes)
	 * @return   0 - success,  others - failed
	 * */
	public native int tmfGetAlgoVersion(byte[] algoVersion);
	
	/**
	 * @author   chen.gs
	 * @category Extract fingerprint feature
	 * @param    ucImageBuf 	- Image data buffer
	 * 			 inWidth    	- Image width
	 * 			 inHeight   	- Image height
	 *           tzBuf      	- Fingerprint feature buffer(1024bytes)
	 *           fmrRecordSize  - actual size of the FMR record
	 * @return   0 - success,  others - failed
	 * */
	public native int tmfGetTzFMR(byte[] ucImageBuf,int inWidth, int inHeight,
			byte[] tzBuf,int[] fmrRecordSize);
	
	/**
	 * @author   chen.gs
	 * @category Fingerprint match
	 * @param  	 mbBuf  - Fingerprint feature buffer
	 *         	 tzBuf  - Fingerprint feature buffer
	 *           level  - Security level(1~5),recommended 3
	 * @return 	 0 - success,  others - failed 	
	 * */
	public native int tmfFingerMatchFMR(byte[] mbBuf,byte[] tzBuf,int level);

}
