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
import type { AppUserRoleRequest } from '../model';
// @ts-ignore
import type { PageAppUserRole } from '../model';
/**
 * RoleApiControllerApi - axios parameter creator
 * @export
 */
export const RoleApiControllerApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        _delete: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('_delete', 'id', id)
            const localVarPath = `/api/role/{id}`
                .replace(`{${"id"}}`, encodeURIComponent(String(id)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'DELETE', ...baseOptions, ...options};
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
         * @param {AppUserRoleRequest} appUserRoleRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        create: async (appUserRoleRequest: AppUserRoleRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'appUserRoleRequest' is not null or undefined
            assertParamExists('create', 'appUserRoleRequest', appUserRoleRequest)
            const localVarPath = `/api/role`;
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
            localVarRequestOptions.data = serializeDataIfNeeded(appUserRoleRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
        /**
         * 
         * @param {string} appClientId 
         * @param {string} [appRole] 
         * @param {string} [username] 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getRoleList: async (appClientId: string, appRole?: string, username?: string, pageNumber?: string, pageSize?: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'appClientId' is not null or undefined
            assertParamExists('getRoleList', 'appClientId', appClientId)
            const localVarPath = `/api/role`;
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;

            if (appClientId !== undefined) {
                localVarQueryParameter['appClientId'] = appClientId;
            }

            if (appRole !== undefined) {
                localVarQueryParameter['appRole'] = appRole;
            }

            if (username !== undefined) {
                localVarQueryParameter['username'] = username;
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
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        read: async (id: string, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('read', 'id', id)
            const localVarPath = `/api/role/{id}`
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
         * @param {AppUserRoleRequest} appUserRoleRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        update: async (id: string, appUserRoleRequest: AppUserRoleRequest, options: RawAxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'id' is not null or undefined
            assertParamExists('update', 'id', id)
            // verify required parameter 'appUserRoleRequest' is not null or undefined
            assertParamExists('update', 'appUserRoleRequest', appUserRoleRequest)
            const localVarPath = `/api/role/{id}`
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
            localVarRequestOptions.data = serializeDataIfNeeded(appUserRoleRequest, localVarRequestOptions, configuration)

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * RoleApiControllerApi - functional programming interface
 * @export
 */
export const RoleApiControllerApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = RoleApiControllerApiAxiosParamCreator(configuration)
    return {
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async _delete(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<{ [key: string]: boolean; }>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator._delete(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RoleApiControllerApi._delete']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {AppUserRoleRequest} appUserRoleRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async create(appUserRoleRequest: AppUserRoleRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUserRole>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.create(appUserRoleRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RoleApiControllerApi.create']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} appClientId 
         * @param {string} [appRole] 
         * @param {string} [username] 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async getRoleList(appClientId: string, appRole?: string, username?: string, pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<PageAppUserRole>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.getRoleList(appClientId, appRole, username, pageNumber, pageSize, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RoleApiControllerApi.getRoleList']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async read(id: string, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUserRole>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.read(id, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RoleApiControllerApi.read']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
        /**
         * 
         * @param {string} id 
         * @param {AppUserRoleRequest} appUserRoleRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async update(id: string, appUserRoleRequest: AppUserRoleRequest, options?: RawAxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AppUserRole>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.update(id, appUserRoleRequest, options);
            const localVarOperationServerIndex = configuration?.serverIndex ?? 0;
            const localVarOperationServerBasePath = operationServerMap['RoleApiControllerApi.update']?.[localVarOperationServerIndex]?.url;
            return (axios, basePath) => createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration)(axios, localVarOperationServerBasePath || basePath);
        },
    }
};

/**
 * RoleApiControllerApi - factory interface
 * @export
 */
export const RoleApiControllerApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = RoleApiControllerApiFp(configuration)
    return {
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        _delete(id: string, options?: RawAxiosRequestConfig): AxiosPromise<{ [key: string]: boolean; }> {
            return localVarFp._delete(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {AppUserRoleRequest} appUserRoleRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        create(appUserRoleRequest: AppUserRoleRequest, options?: RawAxiosRequestConfig): AxiosPromise<AppUserRole> {
            return localVarFp.create(appUserRoleRequest, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} appClientId 
         * @param {string} [appRole] 
         * @param {string} [username] 
         * @param {string} [pageNumber] 
         * @param {string} [pageSize] 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        getRoleList(appClientId: string, appRole?: string, username?: string, pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig): AxiosPromise<PageAppUserRole> {
            return localVarFp.getRoleList(appClientId, appRole, username, pageNumber, pageSize, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        read(id: string, options?: RawAxiosRequestConfig): AxiosPromise<AppUserRole> {
            return localVarFp.read(id, options).then((request) => request(axios, basePath));
        },
        /**
         * 
         * @param {string} id 
         * @param {AppUserRoleRequest} appUserRoleRequest 
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        update(id: string, appUserRoleRequest: AppUserRoleRequest, options?: RawAxiosRequestConfig): AxiosPromise<AppUserRole> {
            return localVarFp.update(id, appUserRoleRequest, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * RoleApiControllerApi - object-oriented interface
 * @export
 * @class RoleApiControllerApi
 * @extends {BaseAPI}
 */
export class RoleApiControllerApi extends BaseAPI {
    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RoleApiControllerApi
     */
    public _delete(id: string, options?: RawAxiosRequestConfig) {
        return RoleApiControllerApiFp(this.configuration)._delete(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {AppUserRoleRequest} appUserRoleRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RoleApiControllerApi
     */
    public create(appUserRoleRequest: AppUserRoleRequest, options?: RawAxiosRequestConfig) {
        return RoleApiControllerApiFp(this.configuration).create(appUserRoleRequest, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} appClientId 
     * @param {string} [appRole] 
     * @param {string} [username] 
     * @param {string} [pageNumber] 
     * @param {string} [pageSize] 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RoleApiControllerApi
     */
    public getRoleList(appClientId: string, appRole?: string, username?: string, pageNumber?: string, pageSize?: string, options?: RawAxiosRequestConfig) {
        return RoleApiControllerApiFp(this.configuration).getRoleList(appClientId, appRole, username, pageNumber, pageSize, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RoleApiControllerApi
     */
    public read(id: string, options?: RawAxiosRequestConfig) {
        return RoleApiControllerApiFp(this.configuration).read(id, options).then((request) => request(this.axios, this.basePath));
    }

    /**
     * 
     * @param {string} id 
     * @param {AppUserRoleRequest} appUserRoleRequest 
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof RoleApiControllerApi
     */
    public update(id: string, appUserRoleRequest: AppUserRoleRequest, options?: RawAxiosRequestConfig) {
        return RoleApiControllerApiFp(this.configuration).update(id, appUserRoleRequest, options).then((request) => request(this.axios, this.basePath));
    }
}

