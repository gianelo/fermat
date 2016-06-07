package org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCountryDependenciesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetJSonObjectException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Country;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.CountryDependency;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.GeolocationPluginRoot;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.config.GeolocationConfiguration;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.CitiesProcessor;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.GeonamesProcessor;
import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.version_1.procesors.GeonosProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class GeolocationPluginManager implements GeolocationManager {

    /**
     * Represents the plugin root class
     */
    GeolocationPluginRoot geolocationPluginRoot;

    /**
     * Represents the plugin file system
     */
    PluginFileSystem pluginFileSystem;

    /**
     * Represents plugin Id.
     */
    UUID pluginId;

    //Configuration variables
    /**
     * Represents the file life span of countries backup.
     */
    FileLifeSpan FILE_LIFE_SPAN = FileLifeSpan.PERMANENT;

    /**
     * Represents the file privacy of countries backup.
     */
    FilePrivacy FILE_PRIVACY = FilePrivacy.PUBLIC;

    /**
     * Constructor with parameters
     * @param geolocationPluginRoot
     * @param pluginFileSystem
     */
    public GeolocationPluginManager(
            GeolocationPluginRoot geolocationPluginRoot,
            PluginFileSystem pluginFileSystem,
            UUID pluginId){
        this.geolocationPluginRoot = geolocationPluginRoot;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    /**
     * This method returns a list of Countries available in external api
     * @return
     */
    @Override
    public HashMap<String, Country> getCountryList()
            throws CantConnectWithExternalAPIException,
            CantCreateBackupFileException,
            CantCreateCountriesListException {

        HashMap<String, Country> countriesList = new HashMap<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                countriesList = createCountriesBackupFile();
                return countriesList;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCountriesData = backupFile.getContent();
            if(stringCountriesData==null||stringCountriesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            countriesList = (HashMap<String, Country>) XMLParser.parseXML(
                    stringCountriesData,
                    countriesList);
            return countriesList;
        } catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the countries list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantCreateCountriesListException(
                    e,
                    "Getting the countries list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates invokes the geonos API and create a file with the countries list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private HashMap<String, Country> createCountriesBackupFile()
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException {
        //We ask for the country list in geonos API
        HashMap<String, Country> countriesList = GeonosProcessor.getCountries();
        //Parse the countries list to XML
        String countriesListXML = XMLParser.parseObject(countriesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.COUNTRIES_BACKUP_FILE,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(countriesListXML);
            backupFile.persistToMedia();
            return countriesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with countries list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with countries list",
                    "Cannot create the backup file in the device");
        }
    }

    /**
     * This method returns the dependencies from a country available in an external api.
     * @param countryCode This code must be defined by the external API, in this version this value could be US for USA, AR for Argentina or VE for Venezuela.
     * @return
     */
    public List<CountryDependency> getCountryDependencies(String countryCode)
            throws CantGetCountryDependenciesListException,
            CantConnectWithExternalAPIException,
            CantCreateBackupFileException {
        List<CountryDependency> countryDependencies = new ArrayList<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_DEPENDENCIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                countryDependencies = createDependenciesBackupFile(countryCode);
                return countryDependencies;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_DEPENDENCIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringDependenciesData = backupFile.getContent();
            if(stringDependenciesData==null||stringDependenciesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            countryDependencies = (List<CountryDependency>) XMLParser.parseXML(
                    stringDependenciesData,
                    countryDependencies);
            return countryDependencies;
        }  catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCountryDependenciesListException(
                    e,
                    "Getting the dependencies list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates invokes the geoname API and create a file with the dependencies list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private List<CountryDependency> createDependenciesBackupFile(String countryCode)
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException,
            CantGetCountryDependenciesListException {
        //We ask for the country list in geonames API
        List<CountryDependency> dependenciesList = GeonamesProcessor.
                getContryDependenciesListByCountryCode(countryCode);
        //Parse the dependencies list to XML
        String dependenciesListXML = XMLParser.parseObject(dependenciesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_COUNTRIES_FILE,
                    GeolocationConfiguration.DEPENDENCIES_BACKUP_FILE + countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(dependenciesListXML);
            backupFile.persistToMedia();
            return dependenciesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with dependencies list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with dependencies list",
                    "Cannot create the backup file in the device");
        }
    }

    /**
     * This method returns the cities by a given country Code
     * @param countryCode This code must be defined by the external API, in this version this value could be US for USA, AR for Argentina or VE for Venezuela.
     * @return
     */
    public List<City> getCitiesByCountryCode(String countryCode)
            throws CantGetCitiesListException {
        List<City> citiesList = new ArrayList<>();
        try{
            boolean backupFileExists = pluginFileSystem.isTextFileExist(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.CITIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            if(!backupFileExists){
                citiesList = createCitiesBackupFile(countryCode);
                return citiesList;
            }
            //The file exists we gonna get the list from backup file
            PluginTextFile backupFile = pluginFileSystem.getTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.CITIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.loadFromMedia();
            String stringCitiesData = backupFile.getContent();
            if(stringCitiesData==null||stringCitiesData.isEmpty()){
                throw new CantCreateCountriesListException(
                        "The backup file is empty");
            }
            citiesList = (List<City>) XMLParser.parseXML(
                    stringCitiesData,
                    citiesList);
            return citiesList;
        }  catch (CantGetJSonObjectException e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Cannot get the data from a Json Object");
        } catch (Exception e) {
            geolocationPluginRoot.reportError(
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new CantGetCitiesListException(
                    e,
                    "Getting the cities list",
                    "Unexpected Exception");
        }
    }

    /**
     * This method creates invokes the geoname API and create a file with the dependencies list
     * @return
     * @throws CantConnectWithExternalAPIException
     * @throws CantGetJSonObjectException
     * @throws CantCreateBackupFileException
     */
    private List<City> createCitiesBackupFile(String countryCode)
            throws CantConnectWithExternalAPIException,
            CantGetJSonObjectException,
            CantCreateBackupFileException,
            CantGetCitiesListException {
        //We ask for the country list in geonames API
        List<City> citiesList = CitiesProcessor.
                getCitiesByCountryCode(countryCode);
        //Parse the dependencies list to XML
        String dependenciesListXML = XMLParser.parseObject(citiesList);
        try{
            //Create file
            PluginTextFile backupFile = pluginFileSystem.createTextFile(
                    pluginId,
                    GeolocationConfiguration.PATH_TO_CITIES_FILE,
                    GeolocationConfiguration.CITIES_BACKUP_FILE+countryCode,
                    FILE_PRIVACY,
                    FILE_LIFE_SPAN);
            backupFile.setContent(dependenciesListXML);
            backupFile.persistToMedia();
            return citiesList;
        } catch (CantPersistFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot persist the backup file in the device");
        } catch (CantCreateFileException e) {
            throw new CantCreateBackupFileException(
                    e,
                    "Creating backup file with cities list",
                    "Cannot create the backup file in the device");
        }
    }

}
