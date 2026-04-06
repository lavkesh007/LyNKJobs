package com.Spring.Student.Configuration;


	import com.cloudinary.Cloudinary;
	import com.cloudinary.utils.ObjectUtils;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	@Configuration
	public class CloudinaryConfig {

	    @Bean
	    public Cloudinary cloudinary() {
	        return new Cloudinary(ObjectUtils.asMap(
	            "cloud_name", "dtaanyrky",
	            "api_key", "434659941689364",
	            "api_secret", "iCDaWa3EEq1FGFgVklN5TSkOJfY"
	        ));
	    }
	
}
