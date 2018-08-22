package com.capgemini.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.capgemini.embeded.AdressData;
import com.capgemini.embeded.AdressData.AdressDataEntityBuilder;
import com.capgemini.types.AdressDataTO;
import com.capgemini.types.AdressDataTO.AdressDataTOBuilder;

public class AdressMapper {
	/**
	 * This is the method which map adress entity to adress TO.
	 * 
	 * @param AdressData
	 *            as adress.
	 * 
	 * @return AdressDataTO Entity.
	 */
	public static AdressDataTO toAdressDataTO(AdressData adressDataEntity) {
		if (adressDataEntity == null)
			return null;

		return new AdressDataTOBuilder().withStreet(adressDataEntity.getStreet())
				.withNumber(adressDataEntity.getNumber()).withCity(adressDataEntity.getCity())
				.withPostCode(adressDataEntity.getPostCode()).build();

	}

	/**
	 * This is the method which map adress TO to adress entity.
	 * 
	 * @param AdressDataTO
	 *            as adress.
	 * 
	 * @return AdressData Entity.
	 */
	public static AdressData toAdressDataEntity(AdressDataTO adressDataTO) {
		if (adressDataTO == null)
			return null;

		return new AdressDataEntityBuilder().withStreet(adressDataTO.getStreet()).withNumber(adressDataTO.getNumber())
				.withCity(adressDataTO.getCity()).withPostCode(adressDataTO.getPostCode()).build();

	}

	/**
	 * This is the method which map list of adresses entity to adresses TO.
	 * 
	 * @param List
	 *            of AdressDataEntities as list of adresses.
	 * 
	 * @return List of adressDataTO.
	 */
	public static List<AdressDataTO> map2TOs(List<AdressData> adressDataEntities) {
		return adressDataEntities.stream().map(AdressMapper::toAdressDataTO).collect(Collectors.toList());
	}

	/**
	 * This is the method which map list of adresses TO to adresses entity.
	 * 
	 * @param List
	 *            of AdressDataTOs as list of adresses.
	 * 
	 * @return List of adressDataEntitis.
	 */
	public static List<AdressData> map2Entities(List<AdressDataTO> adressDataTOs) {
		return adressDataTOs.stream().map(AdressMapper::toAdressDataEntity).collect(Collectors.toList());
	}

}
