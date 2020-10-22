package ru.spbstu.telematics.malyarenko.lab_1;

import java.security.SecureRandom;

public class RandomUuid {
	
	//Порядковые номера байтов UUID, в которых расположен код версии и варианта
	public static final byte UUID_VERSION_POS = 6;
	public static final byte UUID_VARIANT_POS = 8;
	
	public static final byte VARIANT_0 = (byte) 0x00;
	public static final byte VARIANT_1 = (byte) 0x80;
	public static final byte VARIANT_2 = (byte) 0xc0;
	
	private static final byte UUID_SIZE = 16;
	private static final byte RANDOM_UUID_VERSION = 4;
	
	private byte[] uuid = new byte[UUID_SIZE];
	private byte uuidVariant;
	private byte uuidVersion; 
	private boolean isModified;
	private boolean isReady;
	
	public RandomUuid(byte variant) {
		if(variant == VARIANT_0 || variant == VARIANT_1 || variant == VARIANT_2) {
			uuidVariant = variant;
		}
		else {
			throw new IllegalArgumentException("Argument is not a UUID variant");
		}
		
		uuidVersion = RANDOM_UUID_VERSION;
		isModified = false;
		isReady = false;
	}
	
	public void generate() {
		SecureRandom random = new SecureRandom();
		random.nextBytes(uuid);
		
		//Добавление 4-х битов версии UUID
		uuid[UUID_VERSION_POS] = (byte) ((uuid[UUID_VERSION_POS] & 0x0f) | (0x0f | (uuidVersion << 4)));
		
		//Добавление 1 или 2 или 3-х битов варианта UUID 
		switch (uuidVariant) {
		case VARIANT_0:
			uuid[UUID_VARIANT_POS] = (byte) (uuid[UUID_VARIANT_POS] & (byte) 0x7f);
			break;
		case VARIANT_1:
			uuid[UUID_VARIANT_POS] = (byte) ((uuid[UUID_VARIANT_POS] & (byte) 0x3f) | uuidVariant);
			break;
		case VARIANT_2:
			uuid[UUID_VARIANT_POS] = (byte) ((uuid[UUID_VARIANT_POS] & (byte) 0x1f) | uuidVariant);
			break;
		}
		
		isModified = true;
		isReady = true;
	}
	
	public void setVariant(byte variant) {
		if(variant == VARIANT_0 || variant == VARIANT_1 || variant == VARIANT_2) {
			uuidVariant = variant;
		}
		else {
			throw new IllegalArgumentException("Argument is not a UUID variant");
		}
		
		isReady = false;
	}
	
	@Override 
	public String toString() {
		if(isReady) {
			isModified = false;
			String uuidStr = new String();
			
			for(byte b : uuid) {
				uuidStr += String.format("%02x", b);
			}
			
			uuidStr = uuidStr.substring(0, 8) + "-" + uuidStr.substring(8, 12) + "-" 
					+ uuidStr.substring(12, 16) + "-" + uuidStr.substring(16, 20) + "-"
					+ uuidStr.substring(20, 32);
			
			return uuidStr;
		}
		else {
			return null;
		}
	}
	
	public byte[] toByte() {
		if(isReady) {
			isModified = false;
			byte[] uuidCopy = uuid.clone();
			return uuidCopy;
		}
		else {
			return null;
		}
	}
	
	public boolean isModified() {
		return isModified;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public byte version() {
		return uuidVersion;
	}
	
	public byte variant() {
		return uuidVariant;
	}
}