import pygame
import time

from pygame.locals import*
from time import sleep
class Sprite(object):
	def __init__(self, x, y, image):
		self.alive = True
		self.image = image
		self.rect = self.image.get_rect()
		self.rect.left = x
		self.rect.top = y
		self.dest_x = x
		self.dest_y = y
	def update(self):
		if self.rect.left < self.dest_x:
			self.rect.left += 1
		if self.rect.left > self.dest_x:
			self.rect.left -= 1
		if self.rect.top < self.dest_y:
			self.rect.top += 4
		if self.rect.top > self.dest_y:
			self.rect.top -= 5
class Mario(Sprite):
	#He only goes forward
	#No looking back B)
	def __init__(self, x, y):
		self.imagePos = 0
		self.imageArray = []
		i = 1
		while i < 6:
			self.imageArray.append(pygame.image.load("mario" + str(i) + ".png"))
			i+=1
		super(Mario, self).__init__(x, y, self.imageArray[1])
		self.grounded = True
	def update(self):
		tempx = self.rect.left
		self.image = self.imageArray[self.imagePos % 5]
		super(Mario, self).update()
		if tempx > self.rect.left:
			self.imagePos -= 1
			if self.imagePos < 0:
				self.imagePos = 5
		elif tempx < self.rect.left:
			self.imagePos += 1
		if self.rect.top > 405:
			self.grounded = True
			self.rect.top = 405
		if not self.grounded:
			self.dest_y += 5
	def collided(self, sprite):
		if isinstance(sprite, Tube):
			if self.rect.bottom - 3 <= sprite.rect.top:
				self.grounded = True
				self.dest_y = sprite.rect.top - 94
			elif self.rect.right + 50 > sprite.rect.left and self.rect.left + 55 < sprite.rect.right:
				self.dest_x = self.rect.left - 1
			else:
				self.dest_x = self.rect.left + 2
	def noColided(self):
		if self.rect.top < 405:
			self.grounded = False
	def fireBallCheck(self, marioPos):
		return

class Tube(Sprite):
	def __init__(self, x, y):
		tubeImage = pygame.image.load("tube.png")
		super(Tube, self).__init__(x, y, tubeImage)
	def collided(self, sprite):
		return
	def fireBallCheck(self, marioPos, fireballs):
		return
class Goomba(Sprite):
	def __init__(self, x, y):
		goombaImage = pygame.image.load("goomba.png")
		super(Goomba, self).__init__(x, y, goombaImage)
		self.right = True
		self.deathTimer = 10
	def update(self):
		if self.deathTimer < 0:
			self.alive = False
		elif self.deathTimer < 10:
			self.image = pygame.image.load("goomba_fire.png")
			self.deathTimer -=1
		elif self.right:
			self.dest_x += 1
		else: 
			self.dest_x -= 1
		super(Goomba, self).update()
	def collided(self, sprite):
		if isinstance(sprite, Fireball):
			self.deathTimer -= 1
		if isinstance(sprite, Tube):
			self.right = not self.right
	#I am aware that these don't do anything, but my linter gets salty at me without them
	def noColided(self):
		return
	def fireBallCheck(self, marioPos):
		return

class Fireball(Sprite):
	def __init__(self, x, y, right):
		self.right = right
		fireballImage = pygame.image.load("fireball.png")
		super(Fireball, self).__init__(x, y, fireballImage)
	def update(self):
		if self.right:
			self.dest_x += 1
			self.rect.left += 6
		else:
			self.dest_x -= 1
			self.rect.left -= 6
		if self.rect.bottom > 505:
			self.dest_y -= 50
		else:
			self.dest_y += 5
		super(Fireball, self).update()
	def collided(self, sprite):
		if not isinstance(sprite, Mario) and not isinstance(sprite, Fireball):
			self.alive = False
	def noColided(self):
		return
	def fireBallCheck(self, marioPos):
		if self.rect.right > marioPos + 800:
			self.alive = False
		
class Model():
	def __init__(self):
		self.dest_x = 0
		self.dest_y = 0
		self.mario = Mario(100,405)
		self.sprites = [self.mario]
		self.sprites.append(Tube(350, 400))
		self.sprites.append(Tube(250, 475))
		self.sprites.append(Tube(750, 370))
		self.sprites.append(Goomba(600, 385))
	def update(self):
		liveBois = []
		for sprite in self.sprites:
			#Tubes don't really do anyhting when they get collided with or update even
			if not isinstance(sprite, Tube):
				anyCollision = False
				for sprite2 in self.sprites:
					if sprite2 != sprite:
						rect = sprite.rect
						if isinstance(sprite, Mario):
							rect = sprite.rect.copy()
							rect.left += 50
						#Why reinvent the wheel? This is the ONE part of the assignment I've been awful at
						collided = rect.colliderect(sprite2.rect)
						if collided and not (isinstance(sprite, Mario) and isinstance(sprite2, Fireball) or (isinstance(sprite, Fireball) and isinstance(sprite2, Mario))):
							anyCollision = True
							sprite.collided(sprite2)
				if not anyCollision:
					sprite.noColided()
				sprite.update()
			if isinstance(sprite, Fireball):
				sprite.fireBallCheck(self.mario.rect.left)
			if sprite.alive:
				liveBois.append(sprite)
		self.sprites = liveBois
	def set_dest(self, pos):
		self.dest_x = pos[0]
		self.dest_y = pos[1]
	def fire(self):
		pos = self.mario.rect
		lastSprite = self.sprites[len(self.sprites) - 1]
		#This keeps you from spamming
		if not (isinstance(lastSprite, Fireball) and pos.right + 60 > lastSprite.rect.left):
			fireball = Fireball(pos.right + 50, pos.top, True)
			self.sprites.append(fireball)

class View():
	def __init__(self, model):
		screen_size = (800,600)
		self.screen = pygame.display.set_mode(screen_size, 32)
		self.model = model
	def update(self):
		self.screen.fill([40,180,240])
		pygame.draw.rect(self.screen, [0, 100, 30], [0,500,800,700])
		marioRect = self.model.mario.rect.copy()
		marioRect.left = 50
		self.screen.blit(self.model.mario.image, marioRect)
		for sprite in self.model.sprites:
			if not isinstance(sprite, Mario):
				tempRect = sprite.rect.copy()
				tempRect.left -= self.model.mario.rect.left
				self.screen.blit(sprite.image, tempRect)
		pygame.display.flip()

class Controller():
	def __init__(self, model):
		self.model = model
		self.keep_going = True

	def update(self):
		for event in pygame.event.get():
			if event.type == QUIT:
				self.keep_going = False
			elif event.type == KEYDOWN:
				if event.key == K_ESCAPE:
					self.keep_going = False
		keys = pygame.key.get_pressed()
		if keys[K_LEFT]:
			self.model.mario.rect.left -= 1
			self.model.mario.dest_x -= 2
		if keys[K_RIGHT]:
			self.model.mario.rect.left += 1
			self.model.mario.dest_x += 2
		if keys[K_UP]:
			if self.model.mario.grounded:
				self.model.mario.rect.top -= 1
				self.model.mario.dest_y -= 400
				self.model.mario.grounded = False
		if keys[K_LCTRL]:
			self.model.fire()

print("Use the arrow keys to move. Press Esc to quit.")
pygame.init()
m = Model()
v = View(m)
c = Controller(m)
while c.keep_going:
	c.update()
	m.update()
	v.update()
	sleep(0.04)
print("Goodbye")