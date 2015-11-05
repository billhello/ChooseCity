package bill.common.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;
import java.util.List;

/**
 * 图片加载
 * 
 * @author Administrator
 * 
 */
public class MyImageLoader {
	/**
	 * 加载图片的ImageLoader，并且可以清除内存和本地缓存
	 * 
	 */
	public static ImageLoader imageLoader;

	/**
	 * 如果内存或缓存中存在图片,则先删除后加载
	 * 
	 * @param imageView
	 * @param imagePath
	 * @param options
	 */
	public static void displayImageIsStorage(String imagePath,
			ImageView imageView, DisplayImageOptions options) {

		// 显示用户头像背景
		imageLoader.displayImage(imagePath, imageView, options,
				new ImageLoadingListener() {
					boolean cacheFound = false;

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						cacheFound = memoryCacheIsExists(imageUri);
						if (!cacheFound) {
							cacheFound = discCacheIsExists(imageUri);
						}
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						if (cacheFound) {
							removeFromMemoryCache(imageUri);
							removeFromDiscCache(imageUri);
						}
						((ImageView) view).setImageDrawable(null);
						imageLoader.displayImage(imageUri, (ImageView) view);
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
					}
				});
	}

	/**
	 * 内存中是否存在图片
	 * 
	 * @param imageUri
	 * @return
	 */
	public static boolean memoryCacheIsExists(String imageUri) {
		List<String> memCache = MemoryCacheUtils.findCacheKeysForImageUri(
				imageUri, imageLoader.getMemoryCache());

		return !memCache.isEmpty();
	}

	/**
	 * 缓存中是否存在图片
	 * 
	 * @param imageUri
	 * @return
	 */
	public static boolean discCacheIsExists(String imageUri) {
		File discCache = DiskCacheUtils.findInCache(imageUri,
				imageLoader.getDiskCache());
		if (discCache != null) {
			return discCache.exists();
		}
		return false;
	}

	/**
	 * 从内存中移除图片
	 * 
	 * @param imageUri
	 */
	public static void removeFromMemoryCache(String imageUri) {
		MemoryCacheUtils
				.removeFromCache(imageUri, imageLoader.getMemoryCache());
	}

	/**
	 * 从缓存中移除
	 * 
	 * @param imageUri
	 */
	public static void removeFromDiscCache(String imageUri) {
		DiskCacheUtils.removeFromCache(imageUri, imageLoader.getDiskCache());
	}
}
