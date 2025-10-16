---
layout: page
title: Developer Guide
---

# TheRealDeal v1.0 - Developer Guide

## Table of Contents

1. [Setting up](#1-setting-up)
2. [Design](#2-design)
   1. [Architecture](#21-architecture)
   2. [UI component](#22-ui-component)
   3. [Logic component](#23-logic-component)
   4. [Model component](#24-model-component)
   5. [Storage component](#25-storage-component)
   6. [Common classes](#26-common-classes)
3. [Implementation](#3-implementation)
   1. [Contact management](#31-contact-management)
   2. [Property management](#32-property-management)
   3. [Client–property linking](#33-clientproperty-linking)
   4. [Contact filtering](#34-contact-filtering)
   5. [Logging](#35-logging)
   6. [Configuration](#36-configuration)
4. [Documentation](#4-documentation)
5. [Testing](#5-testing)
6. [Dev Ops](#6-dev-ops)
7. [Appendix A: Product Scope](#appendix-a-product-scope)
8. [Appendix B: User Stories](#appendix-b-user-stories)
9. [Appendix C: Use Cases](#appendix-c-use-cases)
10. [Appendix D: Non-Functional Requirements](#appendix-d-non-functional-requirements)
11. [Appendix E: Glossary](#appendix-e-glossary)
12. [Appendix F: Instructions for Manual Testing](#appendix-f-instructions-for-manual-testing)
13. [Appendix G: Efforts](#appendix-g-efforts)

## 1. Setting up

Refer to the [Setting up guide](SettingUp.md) for prerequisites, project import instructions, and verification steps.

## 2. Design
To be updated.

## 3. Implementation

### 3.1. Contact management

`AddCommand` (`addcontact`) registers a new client after generating a UUID. Contacts require at least a name and phone
number, and duplicates are detected by name + phone. `DeleteCommand` removes a selected client by index, while
`EditCommand` applies partial updates.

### 3.2. Property management

`AddPropertyCommand` ingests address, postal code, property characteristics, and an owner UUID. Duplicate properties are
rejected if the address/postal combination already exists. `DeletePropertyCommand` removes properties by their generated
ID. `ShowPropertiesCommand` filters the property list to only those owned by a given client UUID.

### 3.3. Client–property linking
To be updated.

### 3.4. Contact filtering
To be updated.

### 3.5. Logging
To be updated.

### 3.6. Configuration
To be updated.


## 4. Documentation

Refer to the [Documentation guide](Documentation.md) for instructions on maintaining the project website and authoring content.


## 5. Testing

Refer to the [Testing guide](Testing.md) for details on running automated tests and the available test suites.


## 6. Dev Ops
To be updated.


## Appendix A: Product Scope
To be updated.


## Appendix B: User Stories
To be updated.


## Appendix C: Use Cases
To be updated.


## Appendix D: Non-Functional Requirements
To be updated.


## Appendix E: Glossary
To be updated.


## Appendix F: Instructions for Manual Testing
To be updated.

## Appendix G: Efforts
To be updated.
