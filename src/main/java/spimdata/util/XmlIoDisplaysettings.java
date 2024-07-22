/*-
 * #%L
 * Repo containing extra settings that can be stored in spimdata file format
 * %%
 * Copyright (C) 2020 - 2024 EPFL
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

package spimdata.util;

import mpicbg.spim.data.SpimDataException;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.generic.base.ViewSetupAttributeIo;
import mpicbg.spim.data.generic.base.XmlIoEntity;
import org.jdom2.Element;

@ViewSetupAttributeIo(name = "displaysettings", type = Displaysettings.class)
public class XmlIoDisplaysettings extends XmlIoEntity<Displaysettings> {

	public static final String DISPLAYSETTINGS_XML_TAG = "Displaysettings";
	public static final String PROJECTION_MODE_XML_TAG = "Projection_Mode";// underscore
																																					// necessary
																																					// for
																																					// valid
																																					// xml
																																					// element
																																					// to
																																					// store
																																					// in
																																					// @see
																																					// DisplaySettings

	public XmlIoDisplaysettings() {
		super(DISPLAYSETTINGS_XML_TAG, Displaysettings.class);
	}

	@Override
	public Element toXml(final Displaysettings ds) {
		final Element elem = super.toXml(ds);
		elem.addContent(XmlHelpers.booleanElement("isset", ds.isSet));
		elem.addContent(XmlHelpers.intArrayElement("color", ds.color));
		elem.addContent(XmlHelpers.doubleElement("min", ds.min));
		elem.addContent(XmlHelpers.doubleElement("max", ds.max));
		elem.addContent(XmlHelpers.textElement(PROJECTION_MODE_XML_TAG,
			ds.projectionMode));
		return elem;
	}

	@Override
	public Displaysettings fromXml(final Element elem) throws SpimDataException {
		final Displaysettings ds = super.fromXml(elem);
		ds.isSet = XmlHelpers.getBoolean(elem, "isset");
		ds.color = XmlHelpers.getIntArray(elem, "color");
		ds.min = XmlHelpers.getDouble(elem, "min");
		ds.max = XmlHelpers.getDouble(elem, "max");
		ds.projectionMode = XmlHelpers.getText(elem, PROJECTION_MODE_XML_TAG);
		return ds;
	}
}
