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
import type { AppUserRole } from '../model';
// @ts-ignore
import type { PageRegisteredClientResponse } from '../model';
// @ts-ignore
import type { RegisteredClientRequest } from '../model';
// @ts-ignore
import type { RegisteredClientResponse } from '../model';
/**
 * AppApiControllerApi - axios parameter creator
 * @export
 */
export const AppApiControllerApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {RegisteredClientRequest} registeredClientRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerCreateRegisteredClient: async (registeredClientRequest: RegisteredClientRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'registeredClientRequest' is not null or undefined
            assertParamExists('appApiControllerCreateRegisteredClient', 'registeredClientRequest', registeredClientRequest)
            const localVarPath = `/api/app`;
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
            localVarRequestOptions.data = serializeDataIfNeeded(registeredClientRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {number} [pageNumber] 
         * @param {number} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerGetAppList: async (pageNumber?: number, pageSize?: number, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            const localVarPath = `/api/app`;
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
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerGetAppRole: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('appApiControllerGetAppRole', 'id', id)
            const localVarPath = `/api/app/{id}/role`
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
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerReadRegisteredClient: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('appApiControllerReadRegisteredClient', 'id', id)
            const localVarPath = `/api/app/{id}`
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
         * @param {string} id 
         * @param {RegisteredClientRequest} registeredClientRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerUpdateRegisteredClient: async (id: string, registeredClientRequest: RegisteredClientRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('appApiControllerUpdateRegisteredClient', 'id', id)
            // verify required parameter 'registeredClientRequest' is not null or undefined
            assertParamExists('appApiControllerUpdateRegisteredClient', 'registeredClientRequest', registeredClientRequest)
            const localVarPath = `/api/app/{id}`
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
            localVarRequestOptions.data = serializeDataIfNeeded(registeredClientRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * AppApiControllerApi - functional programming interface
 * @export
 */
export const AppApiControllerApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = AppApiControllerApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {RegisteredClientRequest} registeredClientRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appApiControllerCreateRegisteredClient(registeredClientRequest: RegisteredClientRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<RegisteredClientResponse>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appApiControllerCreateRegisteredClient(registeredClientRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppApiControllerApi.appApiControllerCreateRegisteredClient']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {number} [pageNumber] 
         * @param {number} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appApiControllerGetAppList(pageNumber?: number, pageSize?: number, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<PageRegisteredClientResponse>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appApiControllerGetAppList(pageNumber, pageSize, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppApiControllerApi.appApiControllerGetAppList']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appApiControllerGetAppRole(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<AppUserRole>>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appApiControllerGetAppRole(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppApiControllerApi.appApiControllerGetAppRole']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appApiControllerReadRegisteredClient(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<RegisteredClientResponse>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appApiControllerReadRegisteredClient(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppApiControllerApi.appApiControllerReadRegisteredClient']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {RegisteredClientRequest} registeredClientRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async appApiControllerUpdateRegisteredClient(id: string, registeredClientRequest: RegisteredClientRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<RegisteredClientResponse>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.appApiControllerUpdateRegisteredClient(id, registeredClientRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['AppApiControllerApi.appApiControllerUpdateRegisteredClient']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * AppApiControllerApi - factory interface
 * @export
 */
export const AppApiControllerApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = AppApiControllerApiFp(configuration)
    return {
        /**
         * 
         * @param {RegisteredClientRequest} registeredClientRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerCreateRegisteredClient(registeredClientRequest: RegisteredClientRequest, options?: RawAxiosRequestConfig): AxiosPromise<RegisteredClientResponse> {
            return localVarFp.appApiControllerCreateRegisteredClient(registeredClientRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {number} [pageNumber] 
         * @param {number} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerGetAppList(pageNumber?: number, pageSize?: number, options?: RawAxiosRequestConfig): AxiosPromise<PageRegisteredClientResponse> {
            return localVarFp.appApiControllerGetAppList(pageNumber, pageSize, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerGetAppRole(id: string, options?: RawAxiosRequestConfig): AxiosPromise<Array<AppUserRole>> {
            return localVarFp.appApiControllerGetAppRole(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerReadRegisteredClient(id: string, options?: RawAxiosRequestConfig): AxiosPromise<RegisteredClientResponse> {
            return localVarFp.appApiControllerReadRegisteredClient(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {RegisteredClientRequest} registeredClientRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        appApiControllerUpdateRegisteredClient(id: string, registeredClientRequest: RegisteredClientRequest, options?: RawAxiosRequestConfig): AxiosPromise<RegisteredClientResponse> {
            return localVarFp.appApiControllerUpdateRegisteredClient(id, registeredClientRequest, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * AppApiControllerApi - object-oriented interface
 * @export
 * @class AppApiControllerApi
 * @extends {BaseAPI}
 */
export class AppApiControllerApi extends BaseAPI {
    /**
     * 
     * @param {RegisteredClientRequest} registeredClientRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppApiControllerApi
     */
    public appApiControllerCreateRegisteredClient(registeredClientRequest: RegisteredClientRequest, options?: RawAxiosRequestConfig) {
        return AppApiControllerApiFp(this.configuration).appApiControllerCreateRegisteredClient(registeredClientRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {number} [pageNumber] 
     * @param {number} [pageSize] 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppApiControllerApi
     */
    public appApiControllerGetAppList(pageNumber?: number, pageSize?: number, options?: RawAxiosRequestConfig) {
        return AppApiControllerApiFp(this.configuration).appApiControllerGetAppList(pageNumber, pageSize, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppApiControllerApi
     */
    public appApiControllerGetAppRole(id: string, options?: RawAxiosRequestConfig) {
        return AppApiControllerApiFp(this.configuration).appApiControllerGetAppRole(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppApiControllerApi
     */
    public appApiControllerReadRegisteredClient(id: string, options?: RawAxiosRequestConfig) {
        return AppApiControllerApiFp(this.configuration).appApiControllerReadRegisteredClient(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {RegisteredClientRequest} registeredClientRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof AppApiControllerApi
     */
    public appApiControllerUpdateRegisteredClient(id: string, registeredClientRequest: RegisteredClientRequest, options?: RawAxiosRequestConfig) {
        return AppApiControllerApiFp(this.configuration).appApiControllerUpdateRegisteredClient(id, registeredClientRequest, options).then((request) => request(this.axios, this.basePath));
    }
}

