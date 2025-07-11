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



/**
 * 
 * @export
 * @interface RegisteredClientRequest
 */
export interface RegisteredClientRequest {
    /**
     * 
     * @type {string}
     * @memberof RegisteredClientRequest
     */
    'clientId'?: string;
    /**
     * 
     * @type {string}
     * @memberof RegisteredClientRequest
     */
    'clientSecret'?: string;
    /**
     * 
     * @type {string}
     * @memberof RegisteredClientRequest
     */
    'clientSecretExpiresAt'?: string;
    /**
     * 
     * @type {string}
     * @memberof RegisteredClientRequest
     */
    'clientName'?: string;
    /**
     * 
     * @type {Set<string>}
     * @memberof RegisteredClientRequest
     */
    'clientAuthenticationMethods'?: Set<string>;
    /**
     * 
     * @type {Set<string>}
     * @memberof RegisteredClientRequest
     */
    'authorizationGrantTypes'?: Set<string>;
    /**
     * 
     * @type {Set<string>}
     * @memberof RegisteredClientRequest
     */
    'redirectUris'?: Set<string>;
    /**
     * 
     * @type {Set<string>}
     * @memberof RegisteredClientRequest
     */
    'scopes'?: Set<string>;
}

