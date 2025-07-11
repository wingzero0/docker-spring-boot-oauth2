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


// May contain unused imports in some cases
// @ts-ignore
import type { PageableObject } from './pageable-object';
// May contain unused imports in some cases
// @ts-ignore
import type { RegisteredClientResponse } from './registered-client-response';
// May contain unused imports in some cases
// @ts-ignore
import type { SortObject } from './sort-object';

/**
 * 
 * @export
 * @interface PageRegisteredClientResponse
 */
export interface PageRegisteredClientResponse {
    /**
     * 
     * @type {number}
     * @memberof PageRegisteredClientResponse
     */
    'totalPages'?: number;
    /**
     * 
     * @type {number}
     * @memberof PageRegisteredClientResponse
     */
    'totalElements'?: number;
    /**
     * 
     * @type {PageableObject}
     * @memberof PageRegisteredClientResponse
     */
    'pageable'?: PageableObject;
    /**
     * 
     * @type {number}
     * @memberof PageRegisteredClientResponse
     */
    'size'?: number;
    /**
     * 
     * @type {Array<RegisteredClientResponse>}
     * @memberof PageRegisteredClientResponse
     */
    'content'?: Array<RegisteredClientResponse>;
    /**
     * 
     * @type {number}
     * @memberof PageRegisteredClientResponse
     */
    'number'?: number;
    /**
     * 
     * @type {Array<SortObject>}
     * @memberof PageRegisteredClientResponse
     */
    'sort'?: Array<SortObject>;
    /**
     * 
     * @type {boolean}
     * @memberof PageRegisteredClientResponse
     */
    'first'?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof PageRegisteredClientResponse
     */
    'last'?: boolean;
    /**
     * 
     * @type {number}
     * @memberof PageRegisteredClientResponse
     */
    'numberOfElements'?: number;
    /**
     * 
     * @type {boolean}
     * @memberof PageRegisteredClientResponse
     */
    'empty'?: boolean;
}

