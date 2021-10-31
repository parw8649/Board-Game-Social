from django.test import TestCase
from django.urls import reverse
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


class EventViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "name": "TestEventName",
        "description": "TestEventDescription"
    }
    delete_data = add_data
    url_name = "event"
    field_compare = "name"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        EventViewTest.add_data["hostUserId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_event(self):
        super()._test_get(self)

    def test_add_event(self):
        super()._test_add(self)

    def test_update_event(self):
        super()._test_update(self)

    def test_delete_event(self):
        super()._test_delete(self)


class PostViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "postBody": "TestPostBody",
        "postType": "TestPostType"
    }
    delete_data = add_data
    url_name = "post"
    field_compare = "postBody"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        PostViewTest.add_data["userId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_post(self):
        super()._test_get(self)

    def test_add_post(self):
        super()._test_add(self)

    def test_update_post(self):
        super()._test_update(self)

    def test_delete_post(self):
        super()._test_delete(self)


class GameViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "gameTitle": "TestGameTitle",
        "genre": "TestGenre",
        "minPlayer": 1,
        "maxPlayer": 2
    }
    delete_data = add_data
    url_name = "game"
    field_compare = "gameTitle"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        DAVTestTemplate.setUpTestData(cls)

    def test_get_game(self):
        super()._test_get(self)

    def test_add_game(self):
        super()._test_add(self)

    def test_update_game(self):
        super()._test_update(self)

    def test_delete_game(self):
        super()._test_delete(self)


class UserToUserViewTest(TestCase, DAVTestTemplate):
    add_data = {}
    delete_data = add_data
    url_name = "user_to_user"
    field_compare = "userOneId"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        UserToUserViewTest.add_data["userOneId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        UserToUserViewTest.add_data["userTwoId"] = User.objects.create(username="TestUsername1", password="TestPassword1").pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_user_to_user(self):
        super()._test_get(self)

    def test_add_user_to_user(self):
        super()._test_add(self)

    def test_delete_user_to_user(self):
        super()._test_delete(self)


class MessageViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "content": "TestContent"
    }
    delete_data = add_data
    url_name = "message"
    field_compare = "content"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        MessageViewTest.add_data["senderId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        MessageViewTest.add_data["receiverId"] = User.objects.create(username="TestUsername1", password="TestPassword1").pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_message(self):
        super()._test_get(self)

    def test_add_message(self):
        super()._test_add(self)

    def test_update_message(self):
        super()._test_update(self)

    def test_delete_message(self):
        super()._test_delete(self)


class EventToUserViewTest(TestCase, DAVTestTemplate):
    add_data = {}
    delete_data = add_data
    url_name = "event_to_user"
    field_compare = "userId"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        EventToUserViewTest.add_data["userId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        EventToUserViewTest.add_data["eventId"] = Event.objects.create(
            name="TestEventName",
            description="TestEventDescription",
            hostUserId=User.objects.create(username="TestUsername1", password="TestPassword1")
        ).pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_event_to_user(self):
        super()._test_get(self)

    def test_add_event_to_user(self):
        super()._test_add(self)

    def test_delete_event_to_user(self):
        super()._test_delete(self)


class CommentViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "content": "TestContent"
    }
    delete_data = add_data
    url_name = "comment"
    field_compare = "content"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        CommentViewTest.add_data["userId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        CommentViewTest.add_data["postId"] = Post.objects.create(
            postBody="TestPostBody",
            postType="TestPostType",
            userId=User.objects.create(username="TestUsername1", password="TestPassword1")
        ).pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_comment(self):
        super()._test_get(self)

    def test_add_comment(self):
        super()._test_add(self)

    def test_update_comment(self):
        super()._test_update(self)

    def test_delete_comment(self):
        super()._test_delete(self)


class GameToUserViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "private": True
    }
    delete_data = add_data
    url_name = "game_to_user"
    field_compare = "userId"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        GameToUserViewTest.add_data["userId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        GameToUserViewTest.add_data["gameId"] = Game.objects.create(
            gameTitle="TestGameTitle",
            genre="TestGenre",
            minPlayer=1,
            maxPlayer=2
        ).pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_game_to_user(self):
        super()._test_get(self)

    def test_add_game_to_user(self):
        super()._test_add(self)

    def test_update_game_to_user(self):
        super()._test_update(self)

    def test_delete_game_to_user(self):
        super()._test_delete(self)


class HostedGameViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "seatsAvailable": 1
    }
    delete_data = add_data
    url_name = "hosted_game"
    field_compare = "seatsAvailable"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        HostedGameViewTest.add_data["eventId"] = Event.objects.create(
            name="TestEventName",
            description="TestEventDescription",
            hostUserId=User.objects.create(username="TestUsername1", password="TestPassword1")
        ).pk
        HostedGameViewTest.add_data["gameId"] = Game.objects.create(
            gameTitle="TestGameTitle",
            genre="TestGenre",
            minPlayer=1,
            maxPlayer=2
        ).pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_hosted_game(self):
        super()._test_get(self)

    def test_add_hosted_game(self):
        super()._test_add(self)

    def test_update_hosted_game(self):
        super()._test_update(self)

    def test_delete_hosted_game(self):
        super()._test_delete(self)


class ReviewViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "content": "TestContent"
    }
    delete_data = add_data
    url_name = "review"
    field_compare = "content"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        ReviewViewTest.add_data["userId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        ReviewViewTest.add_data["gameId"] = Game.objects.create(
            gameTitle="TestGameTitle",
            genre="TestGenre",
            minPlayer=1,
            maxPlayer=2
        ).pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_review(self):
        super()._test_get(self)

    def test_add_review(self):
        super()._test_add(self)

    def test_update_review(self):
        super()._test_update(self)

    def test_delete_review(self):
        super()._test_delete(self)


class HostedGameToUserViewTest(TestCase, DAVTestTemplate):
    add_data = {
        "userId": 0,
        "gameId": 0
    }
    delete_data = add_data
    url_name = "hosted_game_to_user"
    field_compare = "gameId"
    client_class = DAVTestTemplate.client_class

    @classmethod
    def setUpTestData(cls):
        HostedGameToUserViewTest.add_data["userId"] = User.objects.create(username="TestUsername", password="TestPassword").pk
        HostedGameToUserViewTest.add_data["gameId"] = Game.objects.create(
            gameTitle="TestGameTitle",
            genre="TestGenre",
            minPlayer=1,
            maxPlayer=2
        ).pk
        DAVTestTemplate.setUpTestData(cls)

    def test_get_hosted_game_to_user(self):
        super()._test_get(self)

    def test_add_hosted_game_to_user(self):
        super()._test_add(self)

    def test_update_hosted_game_to_user(self):
        super()._test_update(self)

    def test_delete_hosted_game_to_user(self):
        super()._test_delete(self)
