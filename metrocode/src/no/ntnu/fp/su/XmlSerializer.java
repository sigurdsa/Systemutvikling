package no.ntnu.fp.su;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import no.ntnu.fp.storage.DBStorage;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 * @author askeland
 */
public class XmlSerializer {
    
	private DBStorage database = new DBStorage();
	
	/**
     * Lager et XML-dokument av en LAC
     * 
     * @param au 	AlarmUnit som skal lages XML av.
     * @return 		et XML-dokument som ser ut som f.eks dette
     * 
     * <LAC>
     * 		<LACid>0001</LACid>
     * 		<alarmUnits>
     * 			<alarmUnit>
     * 				<id>1234</id>
     * 				<isReal>EKTE ALARM</isReal>
     *    			<romNavn>STUE</romNavn>
     *    			<romBeskrivelse>Møblert</romBeskrivelse>
     * 			</alarmUnit> 
     * 		</alarmUnits>
	 * </LAC>
     */
	public Document toXml(LocalControl aLAC) {
		Element root = new Element("LAC");
		Element id = new Element("LACid");
		id.appendChild(aLAC.getId());
		root.appendChild(id);
		Element alarms = new Element("alarmUnits");
		root.appendChild(alarms);
		Iterator it = aLAC.getAlarmUnits().iterator();
		while (it.hasNext()) {
			AlarmUnit au = (AlarmUnit)it.next();
			Element element = alarmUnitToXml(au);
			alarms.appendChild(element);
		}
		
		return new Document(root);
	}
	
	public LocalControl toLAC(Document xmlDocument) throws ParseException {
		String id;
		Element LACElement = xmlDocument.getRootElement();
		Element idElement = LACElement.getFirstChildElement("LACid");
		if (idElement == null) {
			// burde kaste en exception...
			return null;
		}
		id = idElement.getValue();
		LocalControl aLAC = new LocalControl(id);
		Element auList = LACElement.getFirstChildElement("alarmUnits");
		Elements alarmUnitElements = auList.getChildElements("alarmUnit");
		
		for (int i = 0; i < alarmUnitElements.size(); i++) {
			Element childElement = alarmUnitElements.get(i);
			aLAC.install(assembleAlarmUnit(childElement));
		}
		
		return aLAC;
	}

    public Document XmlStringToDocument(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
    	nu.xom.Builder parser = new nu.xom.Builder(false);
    	nu.xom.Document doc = parser.build(xml, "");
    	return doc;
    }
	
	public AlarmUnit toAlarmUnit(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
    	nu.xom.Builder parser = new nu.xom.Builder(false);
    	nu.xom.Document doc = parser.build(xml, "");
    	return assembleAlarmUnit(doc.getRootElement());
    }
	
    /**
     * Lager et XML-element av et AlarmUnit-objekt
     * 
     * @param au 	AlarmUnit som skal lages XML av.
     * @return 		et <alarmUnit>-XMLelement som ser ut som f.eks dette
     * 
     * <alarmUnit>
     * 		<id>1234</id>
     * 		<isReal>EKTE ALARM</isReal>
     *    	<rom>STUE - type, møblert</rom>
     * </alarmUnit> 
     */
	public Element alarmUnitToXml(AlarmUnit au) {
		//DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		Element element = new Element("alarmUnit");
		Element id = new Element("id");
		id.appendChild(au.getId());
		Element isReal = new Element("isReal");
		isReal.appendChild(au.getSensor().isFalseAlarm() ? "FALSK ALARM" : "EKTE ALARM");
		Element romNavn = new Element("rom");
		romNavn.appendChild(database.getRoomDesc(au));
		element.appendChild(id);
		element.appendChild(isReal);
		element.appendChild(romNavn);
		return element;
	}
	
	/**
	 * Denne metoden lager et AlarmUnit-objekt av et XML-element som ser sånn ut:
	 * <alarmUnit>
     * 		<id>1234</id>
     * </alarmUnit> 
	 * 
	 * @param auElement		XML-element det skal lages en AlarmUnit av
	 * @return				et AlarmUnit-objekt
	 * @throws ParseException
	 */
	private AlarmUnit assembleAlarmUnit(Element auElement) throws ParseException {
		String id = null;
		Element element = auElement.getFirstChildElement("id");
		if (element != null) {
			id = element.getValue();
		}
		return new AlarmUnit(id);
	}
	
	/**
	 * TODO: handle this one to avoid duplicate code
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private Date parseDate(String date) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		return format.parse(date);
	}

}

