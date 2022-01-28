# coding: utf-8

from __future__ import absolute_import
import unittest

from flask import json
from six import BytesIO

from openapi_server.models.cat import Cat  # noqa: E501
from openapi_server.models.cats_response import CatsResponse  # noqa: E501
from openapi_server.test import BaseTestCase


class TestCatController(BaseTestCase):
    """CatController integration test stubs"""

    def test_add_cat(self):
        """Test case for add_cat

        Add a new cat to the store
        """
        body = {
  "name" : "Garfield",
  "id" : "id",
  "breed" : "Abyssinian"
}
        headers = { 
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
        response = self.client.open(
            '/cats',
            method='POST',
            headers=headers,
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_delete_cat(self):
        """Test case for delete_cat

        Delete a cat
        """
        headers = { 
        }
        response = self.client.open(
            '/cats/{cat_id}'.format(cat_id='cat_id_example'),
            method='DELETE',
            headers=headers)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_get_cat(self):
        """Test case for get_cat

        Retrieve a single cat
        """
        headers = { 
            'Accept': 'application/json',
        }
        response = self.client.open(
            '/cats/{cat_id}'.format(cat_id='cat_id_example'),
            method='GET',
            headers=headers)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_list_cats(self):
        """Test case for list_cats

        Retrieves the list of all cats
        """
        headers = { 
            'Accept': 'application/json',
        }
        response = self.client.open(
            '/cats',
            method='GET',
            headers=headers)
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))

    def test_update_cat(self):
        """Test case for update_cat

        Update an existing cat
        """
        body = {
  "name" : "Garfield",
  "id" : "id",
  "breed" : "Abyssinian"
}
        headers = { 
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        }
        response = self.client.open(
            '/cats/{cat_id}'.format(cat_id='cat_id_example'),
            method='PUT',
            headers=headers,
            data=json.dumps(body),
            content_type='application/json')
        self.assert200(response,
                       'Response body is : ' + response.data.decode('utf-8'))


if __name__ == '__main__':
    unittest.main()
