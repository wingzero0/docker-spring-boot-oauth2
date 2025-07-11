/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import type { Configuration } from '../configuration';
import type { AxiosPromise, AxiosInstance, RawAxiosRequestConfig } from 'axios';
import globalAxios from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import { DUMMY_BASE_URL, assertParamExists, setApiKeyToObject, setBasicAuthToObject, setBearerAuthToObject, setOAuthToObject, setSearchParams, serializeDataIfNeeded, toPathString, createRequestFunction } from '../common';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, type RequestArgs, BaseAPI, RequiredError, operationServerMap } from '../base';
// @ts-ignore
import type { AppUser } from '../model';
// @ts-ignore
import type { PageAppUser } from '../model';
/**
 * AppUserApiControllerApi - axios parameter creator
 * @export
 */
export const AppUserApiControllerApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {AppUser} appUser 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerCreate: async (appUser: AppUser, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'appUser' is not null or undefined
            assertParamExists('appUserApiControllerCreate', 'appUser', appUser)
            const localVarPath = `/api/appUser`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(appUser, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerGetAllAppUser: async (options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/api/appUsers`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerGetAppList: async (pageNumber?: string, pageSize?: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/api/appUser`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            if (pageNumber !== undefined) {
                localVarQueryParameter['pageNumber'] = pageNumber;
            }

            if (pageSize !== undefined) {
                localVarQueryParameter['pageSize'] = pageSize;
            }


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} username 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerGetByUsername: async (username: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'username' is not null or undefined
            assertParamExists('appUserApiControllerGetByUsername', 'username', username)
            const localVarPath = `/api/appUserByUsername/{username}`
                .replace(`{${"username"}}`, encodeURIComponent(String(username)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {number} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerRead: async (id: number, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('appUserApiControllerRead', 'id', id)
            const localVarPath = `/api/appUser/{id}`
                .replace(`{${"id"}}`, encodeURIComponent(String(id)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} name 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerSearchByUsername: async (name: string, pageNumber?: string, pageSize?: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'name' is not null or undefined
            assertParamExists('appUserApiControllerSearchByUsername', 'name', name)
            const localVarPath = `/api/appUserSearch`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            if (name !== undefined) {
                localVarQueryParameter['name'] = name;
            }

            if (pageNumber !== undefined) {
                localVarQueryParameter['pageNumber'] = pageNumber;
            }

            if (pageSize !== undefined) {
                localVarQueryParameter['pageSize'] = pageSize;
            }


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {number} id 
         * @param {AppUser} appUser 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerUpdate: async (id: number, appUser: AppUser, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('appUserApiControllerUpdate', 'id', id)
            // verify required parameter 'appUser' is not null or undefined
            assertParamExists('appUserApiControllerUpdate', 'appUser', appUser)
            const localVarPath = `/api/appUser/{id}`
                .replace(`{${"id"}}`, encodeURIComponent(String(id)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'PUT', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            localVarHeaderParameter['Content-Type'] = 'application/json';

            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};
            localVarRequestOptions.data = serializeDataIfNeeded(appUser, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * AppUserApiControllerApi - functional programming interface
 * @export
 */
export const AppUserApiControllerApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = AppUserApiControllerApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {AppUser} appUser 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerCreate(appUser: AppUser, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUser>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerCreate(appUser, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerCreate']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerGetAllAppUser(options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<object>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerGetAllAppUser(options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerGetAllAppUser']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerGetAppList(pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<PageAppUser>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerGetAppList(pageNumber, pageSize, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerGetAppList']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} username 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerGetByUsername(username: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUser>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerGetByUsername(username, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerGetByUsername']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {number} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerRead(id: number, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUser>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerRead(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerRead']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} name 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerSearchByUsername(name: string, pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<PageAppUser>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerSearchByUsername(name, pageNumber, pageSize, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerSearchByUsername']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {number} id 
         * @param {AppUser} appUser 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appUserApiControllerUpdate(id: number, appUser: AppUser, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUser>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appUserApiControllerUpdate(id, appUser, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppUserApiControllerApi.appUserApiControllerUpdate']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * AppUserApiControllerApi - factory interface
 * @export
 */
export const AppUserApiControllerApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = AppUserApiControllerApiFp(configuration)
    return {
        /**
         * 
         * @param {AppUser} appUser 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerCreate(appUser: AppUser, options?: RawAxiosRequestConfig): AxiosPromise<AppUser> {
            return localVarFp.appUserApiControllerCreate(appUser, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerGetAllAppUser(options?: RawAxiosRequestConfig): AxiosPromise<object> {
            return localVarFp.appUserApiControllerGetAllAppUser(options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerGetAppList(pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig): AxiosPromise<PageAppUser> {
            return localVarFp.appUserApiControllerGetAppList(pageNumber, pageSize, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} username 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerGetByUsername(username: string, options?: RawAxiosRequestConfig): AxiosPromise<AppUser> {
            return localVarFp.appUserApiControllerGetByUsername(username, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {number} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerRead(id: number, options?: RawAxiosRequestConfig): AxiosPromise<AppUser> {
            return localVarFp.appUserApiControllerRead(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} name 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerSearchByUsername(name: string, pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig): AxiosPromise<PageAppUser> {
            return localVarFp.appUserApiControllerSearchByUsername(name, pageNumber, pageSize, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {number} id 
         * @param {AppUser} appUser 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appUserApiControllerUpdate(id: number, appUser: AppUser, options?: RawAxiosRequestConfig): AxiosPromise<AppUser> {
            return localVarFp.appUserApiControllerUpdate(id, appUser, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * AppUserApiControllerApi - object-oriented interface
 * @export
 * @class AppUserApiControllerApi
 * @extends {BaseAPI}
 */
export class AppUserApiControllerApi extends BaseAPI {
    /**
     * 
     * @param {AppUser} appUser 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerCreate(appUser: AppUser, options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerCreate(appUser, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerGetAllAppUser(options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerGetAllAppUser(options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} [pageNumber] 
     * @param {string} [pageSize] 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerGetAppList(pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerGetAppList(pageNumber, pageSize, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} username 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerGetByUsername(username: string, options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerGetByUsername(username, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {number} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerRead(id: number, options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerRead(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} name 
     * @param {string} [pageNumber] 
     * @param {string} [pageSize] 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerSearchByUsername(name: string, pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerSearchByUsername(name, pageNumber, pageSize, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {number} id 
     * @param {AppUser} appUser 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppUserApiControllerApi
     */
    public appUserApiControllerUpdate(id: number, appUser: AppUser, options?: RawAxiosRequestConfig) {
        return AppUserApiControllerApiFp(this.configuration).appUserApiControllerUpdate(id, appUser, options).then((request) => request(this.axios, this.basePath));
    }
}

