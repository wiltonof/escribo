package com.escribo.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import com.escribo.common.foundation.model.IModel;



@Configurable
public class AuxiliaryFunctions {
	
	public static final int BEGIN = 0;
	public static final int END = 1;
	private static AuxiliaryFunctions auxiliaryFunctions;
	@Value("${general.no_photo_path}")
	private String noImagePath;
	
	private AuxiliaryFunctions() {
		super();
	}

	public static AuxiliaryFunctions getInstance() {
		if (auxiliaryFunctions == null) {
			auxiliaryFunctions = new AuxiliaryFunctions();
		}
		return auxiliaryFunctions;
	}

	public String getNoImagePath() {
		return noImagePath;
	}

	public void setNoImagePath(String noImagePath) {
		this.noImagePath = noImagePath;
	}

	public static Double converterDMSToDecimal(String value, String type) {

		Double coordinate = 0d;

		if (value != null && !value.equals("")) {
			Double degrees = 0d;
			Double minutes = 0d;
			Double seconds = 0d;
			String position = value.substring(0, 1);

			if (type.equals("lat")) {
				if (position.equals("-")) {
					degrees = Double.parseDouble(value.substring(1, 3));
					minutes = Double.parseDouble(value.substring(5, 7));
					seconds = Double.parseDouble(value.substring(9, 16));
				} else {
					degrees = Double.parseDouble(value.substring(0, 2));
					minutes = Double.parseDouble(value.substring(4, 6));
					seconds = Double.parseDouble(value.substring(8, 15));
				}

			} else if (type.equals("lon")) {
				if (position.equals("-")) {
					degrees = Double.parseDouble(value.substring(1, 4));
					minutes = Double.parseDouble(value.substring(6, 8));
					seconds = Double.parseDouble(value.substring(10, 17));
				} else {
					degrees = Double.parseDouble(value.substring(0, 3));
					minutes = Double.parseDouble(value.substring(5, 7));
					seconds = Double.parseDouble(value.substring(9, 16));
				}

			}

			if (type.equals("lat") && (degrees < 0 || degrees > 90)) {
				coordinate = null;
			} else if (type.equals("lon") && (degrees < 0 || degrees > 180)) {
				coordinate = null;
			} else if (minutes < 0 || minutes >= 60) {
				coordinate = null;
			} else if (seconds < 0 || seconds >= 60) {
				coordinate = null;
			}

			if (coordinate != null) {
				coordinate = degrees + (minutes / 60) + (seconds / 3600);
				if (position.equals("-"))
					coordinate = coordinate * (-1);
			}
		}
		return coordinate;
	}

	public static String converterDecimalToDMS(String value, String type) {

		if (value != null && !value.equals("")) {
			Double coordinate = Double.parseDouble(value);
			Double degrees = 0d;
			Double minutes = 0d;
			Double seconds = 0d;
			String position = "";

			if (value.charAt(0) == '-') {
				position = "-";
			} else {
				position = "+";
			}

			Double absValue = Math.abs(coordinate);
			degrees = Math.floor(absValue);
			seconds = (absValue - degrees) * 3600;
			minutes = Math.floor(seconds / 60);
			seconds = (seconds - (minutes * 60));

			String result = formatNumber(degrees, 0);

			if (result.length() == 1) {
				result = "0" + result;
			}

			if (type.equals("lon") && result.length() == 2) {
				result = "0" + result;
			}

			result = result.concat("  ");

			if (formatNumber(minutes, 0).length() == 1) {
				result = result.concat(("0" + formatNumber(minutes, 0)));
			} else {
				result = result.concat(formatNumber(minutes, 0));
			}

			result = result.concat("\" ");

			String secondsStr = formatNumber(seconds, 4);
			if (!secondsStr.contains(",")) {
				secondsStr = secondsStr + ",0";
			}
			String[] secondsStrArray = secondsStr.split(",");
			if (secondsStrArray[0].length() == 1) {
				secondsStrArray[0] = "0" + secondsStrArray[0];
			}

			if (secondsStrArray[1].length() == 1) {
				secondsStrArray[1] = secondsStrArray[1] + "000";
			} else if (secondsStrArray[1].length() == 2) {
				secondsStrArray[1] = secondsStrArray[1] + "00";
			}
			if (secondsStrArray[1].length() == 3) {
				secondsStrArray[1] = secondsStrArray[1] + "0";
			}

			result = result.concat(secondsStrArray[0] + "." + secondsStrArray[1]);

			result = result.concat("\"");

			if (position.equals("-")) {
				result = position + result;
			}

			return result;

		} else {
			return "";
		}
	}

	public static String formatNumber(Double number, int precision) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ITALY);
		otherSymbols.setDecimalSeparator(',');
		otherSymbols.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(otherSymbols);
		df.setMaximumFractionDigits(precision);
		return df.format(number);
	}

	public static String formatDateTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return df.format(date);
	}

	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	
	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}
	
	public static String calcCheckSumEAN13(String seq)
	{
		String res = "";
		
		if ((seq.length() > 0)&&(seq.length() <= 12))
		{
			if (isNumeric(seq))
			{
				if (seq.length() < 12)
				{
					seq = fillChar(seq, '0', BEGIN, 12);
				}
				
				String[] aux = seq.split("");
				int sum = 0;
				
				for (int i = 1; i < aux.length; i++) 
				{
					try 
					{
						if ((i % 2) == 0)
						{
							sum = sum + 3*Integer.parseInt(aux[i]);
						}
						else
						{
							sum = sum + Integer.parseInt(aux[i]);
						}
					} 
					catch (Exception e) {}
				}
				
				int tenMult = (sum / 10)*10;
				
				if (tenMult < sum) tenMult = tenMult + 10;
				
				res = Integer.toString(tenMult - sum);
			}
		}
		
		return res;
	}
	
	public static String getSeqWithCheckSumEAN13(String seq)
	{
		return seq + calcCheckSumEAN13(seq);
	}
	
	public static final String fillChar(String str, char character, int direction, int str_length)
	{
		
		String aux = str;
		
		while (aux.length() < str_length) 
		{			
			if (direction == BEGIN)
			{
				aux = character + aux;
			}
			else if (direction == END)
			{
				aux = aux + character;
			}			
		}
		
		return aux;		
	}
	
	public static double calcDistance(double origLat, double origLng, double destLat, double destLng)
	{  
        double dlon, dlat, a, distancia;  
        dlon = Math.toRadians(destLng) - Math.toRadians(origLng);  
        dlat = Math.toRadians(destLat) - Math.toRadians(origLat);  
        a = Math.pow(Math.sin(dlat/2),2) + Math.cos(Math.toRadians(origLat)) * Math.cos(Math.toRadians(destLat)) * Math.pow(Math.sin(dlon/2),2);  
        distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));  
        return 6371000 * distancia; /* 6378140 is the radius of the Earth in meters*/  
    }
	
	// convert from UTF-8 -> internal Java String format
    public static String convertFromUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
 
    // convert from internal Java String format -> UTF-8
    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    public static boolean isNullOrEmpty(List<IModel> values){
    	if(values != null && !values.isEmpty()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static String queryNavigation(String action, Map<String, String> values, boolean isRedirect){
    	String value = action+"?";
    	for(String key :values.keySet()){
    		value +=key+"="+values.get(key)+"&";
    	}
    	if(isRedirect)
    		return value+"faces-redirect=true";    	
    	else
    		return value+"faces-redirect=false";
    }

  
    private static UIComponent findComponentInRoot(String id) {  
        UIComponent ret = null;  
  
        FacesContext context = FacesContext.getCurrentInstance();  
        if (context != null) {  
            UIComponent root = context.getViewRoot();  
            ret = root.findComponent(id);  
        }  
  
        return ret;  
    } 
    
    public static String getValueHiddenField(String id){
    	UIComponent ui = findComponentInRoot(id);
    	if(ui != null ){
    		try{
    			return (String)((HtmlInputHidden) findComponentInRoot(id)).getValue(); 
    		}catch(Exception e){
    			return null;
    		}
    	}else{
    		return null;
    	}
    	
    }
	public static String getStringHash(String value){
        try {
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(value.getBytes(),0,value.length());
            return new BigInteger(1,m.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static String getFileHash(File file){
        String hashGenerated = null;
        java.security.MessageDigest md = null;
        try {
            md = java.security.MessageDigest.getInstance("MD5");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            StringBuilder hexFileCSV = new StringBuilder();
            int theByte = 0;
            while ((theByte = in.read()) != -1) {
                // chamar o método update() para passar os dados a serem criptografados
                md.update((byte) theByte);
            }
            in.close();
            byte[] theDigest = md.digest();
            for(int i = 0; i < theDigest.length; i++){
                if ((0xff & theDigest[i]) < 0x10){
                    hexFileCSV.append("0" + Integer.toHexString((0xFF & theDigest[i])));
                } else {
                    hexFileCSV.append(Integer.toHexString(0xFF & theDigest[i]));
                }
            }

            hashGenerated = hexFileCSV.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashGenerated;
    }
	
	public static String getFileHash( byte[] bytes){
        try {   
            MessageDigest m=MessageDigest.getInstance("MD5");
            m.update(bytes,0,bytes.length);
            return new BigInteger(1,m.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}