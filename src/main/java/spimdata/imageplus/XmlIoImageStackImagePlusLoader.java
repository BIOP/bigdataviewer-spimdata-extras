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

package spimdata.imageplus;

import ij.IJ;
import ij.ImagePlus;
import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.generic.sequence.ImgLoaderIo;
import mpicbg.spim.data.generic.sequence.XmlIoBasicImgLoader;
import net.imglib2.img.basictypeaccess.array.ArrayDataAccess;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.NumericType;
import org.jdom2.Element;

import java.io.File;

import static mpicbg.spim.data.XmlKeys.IMGLOADER_FORMAT_ATTRIBUTE_NAME;

@ImgLoaderIo(format = "spimreconstruction.biop_imagestackimageplusloader",
	type = ImageStackImageLoaderTimeShifted.class)
public class XmlIoImageStackImagePlusLoader<T extends NumericType<T> & NativeType<T>, A extends ArrayDataAccess<A>>
	implements XmlIoBasicImgLoader<ImageStackImageLoaderTimeShifted<T, A>>
{

	final public static String IMAGEPLUS_FILEPATH_TAG = "imageplus_filepath";
	final public static String IMAGEPLUS_TIME_ORIGIN_TAG =
		"imageplus_time_origin";

	@Override
	public Element toXml(ImageStackImageLoaderTimeShifted imgLoader,
		File basePath)
	{
		final Element elem = new Element("ImageLoader");
		elem.setAttribute(IMGLOADER_FORMAT_ATTRIBUTE_NAME, this.getClass()
			.getAnnotation(ImgLoaderIo.class).format());
		// For potential extensibility
		elem.addContent(XmlHelpers.textElement(IMAGEPLUS_FILEPATH_TAG, imgLoader
			.getImagePlus().getOriginalFileInfo().getFilePath()));
		elem.addContent(XmlHelpers.intElement(IMAGEPLUS_TIME_ORIGIN_TAG, imgLoader
			.getTimeShift()));
		return elem;
	}

	@Override
	public ImageStackImageLoaderTimeShifted fromXml(Element elem, File basePath,
		AbstractSequenceDescription<?, ?, ?> sequenceDescription)
	{

		String imagePlusFilePath = XmlHelpers.getText(elem, IMAGEPLUS_FILEPATH_TAG);
		int originTimePoint = XmlHelpers.getInt(elem, IMAGEPLUS_TIME_ORIGIN_TAG);

		ImagePlus imp = IJ.openImage(imagePlusFilePath);

		final ImageStackImageLoaderTimeShifted imgLoader;

		{
			switch (imp.getType()) {
				case ImagePlus.GRAY8:
					imgLoader = ImageStackImageLoaderTimeShifted
						.createUnsignedByteInstance(imp, originTimePoint);
					break;
				case ImagePlus.GRAY16:
					imgLoader = ImageStackImageLoaderTimeShifted
						.createUnsignedShortInstance(imp, originTimePoint);
					break;
				case ImagePlus.GRAY32:
					imgLoader = ImageStackImageLoaderTimeShifted.createFloatInstance(imp,
						originTimePoint);
					break;
				case ImagePlus.COLOR_RGB:
				default:
					imgLoader = ImageStackImageLoaderTimeShifted.createARGBInstance(imp,
						originTimePoint);
					break;
			}
		}

		return imgLoader;
	}
}
