/*-
 * #%L
 * Spimdata-Extra
 * %%
 * Copyright (C) 2019 - 2020 Nicolas Chiaruttini, EPFL
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
