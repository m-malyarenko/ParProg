package ru.spbstu.telematics.malyarenko.lab_1;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.ArrayList;

public class RandomUuidTest 
{	
	private RandomUuid uuidGen0;
	private RandomUuid uuidGen1;
	private RandomUuid uuidGen2;
	private ArrayList<RandomUuid> uuidGenList;
	
	@Before
	public void setUp() {
		uuidGen0 = new RandomUuid(RandomUuid.VARIANT_0);
		uuidGen1 = new RandomUuid(RandomUuid.VARIANT_1);
		uuidGen2 = new RandomUuid(RandomUuid.VARIANT_2);
		uuidGenList = new ArrayList<RandomUuid>();
		
		uuidGenList.add(uuidGen0);
		uuidGenList.add(uuidGen1);
		uuidGenList.add(uuidGen2);
	}
	
    @Test
    public void versionTest()
    {
    	byte expectedVersion = 0x40;
    	byte actualVersion;
    	
    	for(RandomUuid u : uuidGenList) {
    		u.generate();
    		
        	actualVersion = (byte) ((u.version() << 4) & 0xf0);
        	assertEquals(expectedVersion, actualVersion);
        	actualVersion = (byte) (u.toByte()[RandomUuid.UUID_VERSION_POS] & 0xf0);
        	assertEquals(expectedVersion, actualVersion);
    	}
    }
    
    @Test
    public void variantTest() {
    	byte actualVariant;
    	
    	uuidGen0.generate();
    	uuidGen1.generate();
    	uuidGen2.generate();
    	
    	actualVariant = (byte) (uuidGen0.variant() & 0x80);
    	assertEquals(RandomUuid.VARIANT_0, actualVariant);
    	actualVariant = (byte) (uuidGen0.toByte()[RandomUuid.UUID_VARIANT_POS] & 0x80);
    	assertEquals(RandomUuid.VARIANT_0, actualVariant);
    	
    	actualVariant = (byte) (uuidGen1.variant() & 0xc0);
    	assertEquals(RandomUuid.VARIANT_1, actualVariant);
    	actualVariant = (byte) (uuidGen1.toByte()[RandomUuid.UUID_VARIANT_POS] & 0xc0);
    	assertEquals(RandomUuid.VARIANT_1, actualVariant);
    	
       	actualVariant = (byte) (uuidGen2.variant() & 0xe0);
    	assertEquals(RandomUuid.VARIANT_2, actualVariant);
    	actualVariant = (byte) (uuidGen2.toByte()[RandomUuid.UUID_VARIANT_POS] & 0xe0);
    	assertEquals(RandomUuid.VARIANT_2, actualVariant);
    }
    
    @Test
    public void isModifiedTest() {
	
    	for (RandomUuid u : uuidGenList) {
    		assertFalse(u.isModified());
    		u.generate();
    		assertTrue(u.isModified());
    		u.toString();
    		assertFalse(u.isModified());
    		u.toByte();
    		assertFalse(u.isModified());
    		u.generate();
    		assertTrue(u.isModified());
    	}
    }
    
    @Test 
    public void isReadyTest() {
    	for (RandomUuid u : uuidGenList) {
    		assertFalse(u.isReady());
    		u.generate();
    		assertTrue(u.isReady());
    		u.setVariant(RandomUuid.VARIANT_0);
    		assertFalse(u.isReady());
    		assertNull(u.toString());
    		assertNull(u.toByte());
    	}
    }
    
    @Test
    public void uuidAreNotEqual() {
    	ArrayList<String> uuidHistory = new ArrayList<String>();
    	
    	uuidGen0.generate();
    	uuidGen1.generate();
    	uuidGen2.generate();
    	
    	assertNotEquals(uuidGen0.toString(), uuidGen1.toString());
    	assertNotEquals(uuidGen0.toString(), uuidGen2.toString());
    	assertNotEquals(uuidGen1.toString(), uuidGen2.toString());
    	
    	uuidHistory.add(uuidGen0.toString());
    	uuidHistory.add(uuidGen1.toString());
    	uuidHistory.add(uuidGen2.toString());
    	
    	//Сравнение нового сгенерированного UUID со всеми предыдущими
    	for (int i = 0; i < 1000; i++) {
    		for(RandomUuid u : uuidGenList) {
    			u.generate();
    			String newStr = u.toString();
    			
    			for(String s : uuidHistory) {
    				assertNotEquals(s, newStr);
    			}
    			uuidHistory.add(newStr);
    		}
    	}
    }
    
    @Test
    public void invalidVarianExceptionTest() throws IllegalArgumentException {
    	RandomUuid uuidQuasimodo;
    	
    	try {
        	uuidQuasimodo = new RandomUuid((byte) 0x66);
    	}
    	catch (IllegalArgumentException thrown){
    		assertEquals("Argument is not a UUID variant", thrown.getMessage());
    	}
    	
    	try {
        	uuidQuasimodo = new RandomUuid(RandomUuid.VARIANT_0);
        	uuidQuasimodo = new RandomUuid(RandomUuid.VARIANT_1);
        	uuidQuasimodo = new RandomUuid(RandomUuid.VARIANT_2);
        	Assert.assertTrue(true);
    	}
    	catch (IllegalArgumentException thrown){
    		Assert.fail("Argument was correct");
    	}
    	
    	try {
    		uuidQuasimodo = new RandomUuid(RandomUuid.VARIANT_0);
			uuidQuasimodo.setVariant((byte) 0x66);
			
		} catch (IllegalArgumentException thrown) {
			assertEquals("Argument is not a UUID variant", thrown.getMessage());
		}
    	
    	try {
    		uuidQuasimodo = new RandomUuid(RandomUuid.VARIANT_0);
			uuidQuasimodo.setVariant(RandomUuid.VARIANT_0);
			uuidQuasimodo.setVariant(RandomUuid.VARIANT_1);
			uuidQuasimodo.setVariant(RandomUuid.VARIANT_2);
			
		} catch (IllegalArgumentException thrown) {
			Assert.fail("Argument was correct");
		}
    }
}
