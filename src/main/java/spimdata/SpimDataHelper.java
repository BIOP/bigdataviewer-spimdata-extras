/*-
 * #%L
 * Repo containing extra settings that can be stored in spimdata file format
 * %%
 * Copyright (C) 2020 - 2021 EPFL
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
package spimdata;

import mpicbg.spim.data.generic.AbstractSpimData;
import mpicbg.spim.data.generic.base.Entity;
import mpicbg.spim.data.generic.base.ViewSetupAttributes;
import mpicbg.spim.data.registration.ViewTransformAffine;
import net.imglib2.realtransform.AffineTransform3D;

/**
 * Various helper methods to manipulate spimdata objects
 */
public class SpimDataHelper {

    public static void removeEntities(AbstractSpimData<?> asd, String... entityNames) {
        asd.getSequenceDescription().getViewSetups().forEach((id, vs) -> {
            for (String entityName:entityNames) {
                vs.getAttributes().remove(entityName.trim());
            }
        });
    }

    public static void removeEntities(AbstractSpimData<?> asd, Class<? extends Entity>... entityClasses) {
        asd.getSequenceDescription().getViewSetups().forEach((id, vs) -> {
            for (Class<? extends Entity> entityClass : entityClasses) {
                vs.getAttributes().remove(ViewSetupAttributes.getNameForClass(entityClass));
            }
        });
    }

    public static void pretransform(AbstractSpimData<?> asd, String name, AffineTransform3D at3d) {
        asd.getViewRegistrations()
            .getViewRegistrations()
            .values()
            .forEach(viewRegistraion -> {
                viewRegistraion.preconcatenateTransform(
                        new ViewTransformAffine(name, at3d));
            });
    }

    public static void scale(AbstractSpimData<?> asd, String name, double scaleX, double scaleY, double scaleZ) {
        AffineTransform3D at3d = new AffineTransform3D();
        at3d.scale(scaleX, scaleY, scaleZ);
        pretransform(asd, name, at3d);
    }

    public static void scale(AbstractSpimData<?> asd, String name, double scale) {
        scale(asd, name, scale, scale, scale);
    }

    public static void translate(AbstractSpimData<?> asd, String name, double translateX, double translateY, double translateZ) {
        AffineTransform3D at3d = new AffineTransform3D();
        at3d.scale(translateX, translateY, translateZ);
        pretransform(asd, name, at3d);
    }

}
