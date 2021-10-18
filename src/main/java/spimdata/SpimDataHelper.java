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
