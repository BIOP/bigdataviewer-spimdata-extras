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

import bdv.viewer.SourceAndConverter;
import mpicbg.spim.data.generic.base.NamedEntity;
import net.imglib2.display.ColorConverter;
import net.imglib2.type.numeric.ARGBType;

/**
 * Entity which stores the display settings of a view setup limited to simple
 * colored LUT + min max display also stores the projection mode
 */

@SuppressWarnings("unused")
public class Displaysettings extends NamedEntity implements
	Comparable<Displaysettings>
{

	// RGBA value
	public int[] color = new int[] { 255, 255, 255, 0 }; // Initialization avoids
																												// null pointer
																												// exception

	// min display value
	public double min = 0;

	// max display value
	public double max = 255;

	// if isset is false, the display value is discarded
	public boolean isSet = false;

	// stores projection mode
	public String projectionMode = "Sum"; // Default projection mode

	public Displaysettings(final int id, final String name) {
		super(id, name);
	}

	public Displaysettings(final int id) {
		this(id, Integer.toString(id));
	}

	/**
	 * Set the name of this displaysettings (probably useless).
	 */
	@Override
	public void setName(final String name) {
		super.setName(name);
	}

	/**
	 * Compares the {@link #getId() ids}.
	 */
	@Override
	public int compareTo(final Displaysettings o) {
		return getId() - o.getId();
	}

	protected Displaysettings() {}

	/**
	 * @return a meaningful String representation of DisplaySettings
	 */
	public String toString() {
		String str = "";
		str += "set = " + this.isSet + ", ";

		if (this.projectionMode != null) str += "set = " + this.projectionMode +
			", ";

		if (this.color != null) {
			str += "color = ";
			for (int j : this.color) {
				str += j + ", ";
			}
		}

		str += "min = " + this.min + ", ";

		str += "max = " + this.max;

		return str;
	}

	/**
	 * Deprecated: please use the correct case for the method
	 * @param sac source
	 * @param ds the displaysettings object which is mutated
	 */
	@Deprecated
	public static void GetDisplaySettingsFromCurrentConverter(
		SourceAndConverter<?> sac, Displaysettings ds)
	{
		getDisplaySettingsFromCurrentConverter(sac, ds);
	}

	/**
	 * Stores display settings currently in use by the SourceAndConverter into the
	 * link SpimData object
	 *
	 * @param sac source
	 * @param ds the displaysettings object which is mutated
	 */
	public static void getDisplaySettingsFromCurrentConverter(
			SourceAndConverter<?> sac, Displaysettings ds)
	{

		// Color + min max
		if (sac.getConverter() instanceof ColorConverter) {
			ColorConverter cc = (ColorConverter) sac.getConverter();
			ds.setName("vs:" + ds.getId());
			int colorCode = cc.getColor().get();
			ds.color = new int[] { ARGBType.red(colorCode), ARGBType.green(colorCode),
					ARGBType.blue(colorCode), ARGBType.alpha(colorCode) };
			ds.min = cc.getMin();
			ds.max = cc.getMax();
			ds.isSet = true;
		}
		else {
			System.err.println("Converter is of class :" + sac.getConverter()
					.getClass().getSimpleName() + " -> Display settings cannot be stored.");
		}
	}

	/**
	 * Apply the display settings to the SourceAndConverter object
	 *
	 * @param sac source
	 * @param ds display settings object
	 * @return for some reason, the projection mode of the display settings object
	 */
	public static String pullDisplaySettings(SourceAndConverter<?> sac,
											 Displaysettings ds)
	{

		if (ds.isSet) {
			if (sac.getConverter() instanceof ColorConverter) {
				ColorConverter cc = (ColorConverter) sac.getConverter();
				cc.setColor(new ARGBType(ARGBType.rgba(ds.color[0], ds.color[1],
						ds.color[2], ds.color[3])));
				cc.setMin(ds.min);
				cc.setMax(ds.max);
				if (sac.asVolatile() != null) {
					cc = (ColorConverter) sac.asVolatile().getConverter();
					cc.setColor(new ARGBType(ARGBType.rgba(ds.color[0], ds.color[1],
							ds.color[2], ds.color[3])));
					cc.setMin(ds.min);
					cc.setMax(ds.max);
				}
			}
			else {
				System.err.println("Converter is of class :" + sac.getConverter()
						.getClass().getSimpleName() +
						" -> Display settings cannot be reapplied.");
			}

			return ds.projectionMode;
		}

		return null;
	}

	/**
	 * Deprecated: please use the correct case for the method
	 * @param sac source
	 * @param ds display settings object
	 * @return for some reason, the projection mode of the display settings object
	 */
	@Deprecated
	public static String PullDisplaySettings(SourceAndConverter<?> sac,
		Displaysettings ds)
	{
		return pullDisplaySettings(sac, ds);
	}

	/**
	 * Apply the display settings to an array of source and converter Silently
	 * ignored if null is found Applies the Displaysettings to the volatile
	 * source, if any
	 *
	 * @param sacs sources
	 * @param ds display settings
	 */
	public static void applyDisplaysettings(SourceAndConverter<?>[] sacs,
		Displaysettings ds)
	{
		if ((sacs != null) && (ds != null)) {
			for (SourceAndConverter<?> sac : sacs) {
				applyDisplaysettings(sac, ds);
			}
		}
	}

	/**
	 * Apply the display settings to an array of source and converter Silently
	 * ignored if null is found Applies the Displaysettings to the volatile
	 * source, if any
	 *
	 * @param sac source
	 * @param ds display settings
	 */
	public static void applyDisplaysettings(SourceAndConverter<?> sac,
		Displaysettings ds)
	{
		if ((sac != null) && (ds != null)) {
			if (sac.getConverter() instanceof ColorConverter) {
				ColorConverter cc = (ColorConverter) sac.getConverter();
				cc.setMin(ds.min);
				cc.setMax(ds.max);
				cc.setColor(new ARGBType(ARGBType.rgba(ds.color[0], ds.color[1],
					ds.color[2], ds.color[3])));
				if (sac.asVolatile() != null) {
					cc = (ColorConverter) sac.asVolatile().getConverter();
					cc.setMin(ds.min);
					cc.setMax(ds.max);
					cc.setColor(new ARGBType(ARGBType.rgba(ds.color[0], ds.color[1],
						ds.color[2], ds.color[3])));
				}
			}
		}
	}

}
