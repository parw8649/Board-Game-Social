from django.test import TestCase
from django.urls import reverse
from rest_framework.authtoken.models import Token
from rest_framework.test import APIClient
from .models import *
from .tests_manager import *


class LoginViewTest(TestCase):
    credentials = {
        'username': 'admin',
        'password': 'admin'
    }
    user = User.objects.get(username=credentials["username"])

    def test_login(self):
        response = self.client.post(reverse('login'), data=LoginViewTest.credentials, follow=True)
        self.assertTrue("token" in decode_content(response.content))
        self.assertTrue(LoginViewTest.user.is_authenticated)
        self.assertEqual(response.status_code, 200)


class UserViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "username": "AutoTestUserUsername",
        "password": "ThePasswordOfUserThatIsUserForTests"
    }
    delete_data = {
        "username": "AutoTestUserUsername"
    }
    url_name = "user"
    field_compare = "username"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        DAVTestTemplate.setUpTestData(cls)

    def test_get_user(self):
        super()._test_get(self)

    def test_add_user(self):
        super()._test_add(self)

    def test_update_user(self):
        super()._test_update(self)

    def test_delete_user(self):
        super()._test_delete(self)

