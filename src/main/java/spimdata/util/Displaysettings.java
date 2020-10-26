/*-
 * #%L
 * Repo containing extra settings that can be stored in spimdata file format
 * %%
 * Copyright (C) 2020 EPFL
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
import mpicbg.spim.data.generic.base.NamedEntity;

/**
 * Entity which stores the display settings of a view setup
 *
 * limited to simple colored LUT + min max display
 *
 * also stores the projection mode
 *
 */

public class Displaysettings extends NamedEntity implements Comparable<Displaysettings>
{
    // RGBA value
    public int[] color = new int[] {255,255,255,0}; // Initialization avoids null pointer exception

    // min display value
    public double min = 0;

    // max display value
    public double max = 255;

    // if isset is false, the display value is discarded
    public boolean isSet = false;

    // stores projection mode
    public String projectionMode = "Sum"; // Default projection mode

    public Displaysettings(final int id, final String name)
    {
        super( id, name );
    }

    public Displaysettings(final int id )
    {
        this( id, Integer.toString( id ) );
    }

    /**
     * Get the unique id of this displaysettings
     */
    @Override
    public int getId()
    {
        return super.getId();
    }

    /**
     * Get the name of this Display Settings Entity.
     */
    @Override
    public String getName()
    {
        return super.getName();
    }

    /**
     * Set the name of this displaysettings (probably useless).
     */
    @Override
    public void setName( final String name )
    {
        super.setName( name );
    }

    /**
     * Compares the {@link #getId() ids}.
     */
    @Override
    public int compareTo( final Displaysettings o )
    {
        return getId() - o.getId();
    }

    protected Displaysettings()
    {}

    /**
     * More meaningful String representation of DisplaySettings
     * @return
     */
    public String toString() {
        String str = "";
        str+="set = "+this.isSet+", ";

        if (this.projectionMode!=null)
            str+="set = "+this.projectionMode+", ";

        if (this.color!=null) {
            str += "color = ";
            for (int i = 0; i < this.color.length;i++) {
                str += this.color[i] + ", ";
            }
        }

        str+="min = "+this.min+", ";

        str+="max = "+this.max;

        return str;
    }

}
